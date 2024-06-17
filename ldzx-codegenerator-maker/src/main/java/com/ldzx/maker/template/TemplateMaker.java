package com.ldzx.maker.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ldzx.maker.meta.Meta;
import com.ldzx.maker.meta.emus.FileGenerateTypeEnum;
import com.ldzx.maker.meta.emus.FileTypeEnum;
import com.ldzx.maker.template.enums.FileFilterRangeEnum;
import com.ldzx.maker.template.enums.FileFilterRuleEnum;
import com.ldzx.maker.template.model.FileFilterConfig;
import com.ldzx.maker.template.model.TemplateMakerFileConfig;
import com.ldzx.maker.template.model.TemplateMakerModelConfig;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 模板生成文件
 */
public class TemplateMaker {
    /**
     * 制作模板
     * @param newMeta
     * @param originProjectPath
     * @param templateMakerFileConfig
     * @param templateMakerModelConfig
     * @param id
     * @return
     */
    public static long makeTemplate(Meta newMeta, String originProjectPath , TemplateMakerFileConfig templateMakerFileConfig, TemplateMakerModelConfig templateMakerModelConfig, Long id){
        //没有id生则生成
        if(id == null){
            id = IdUtil.getSnowflakeNextId();
        }
        //复制目录
        //templatePath:生成的目标文件夹
        String projectPath = System.getProperty("user.dir") + File.separator + "ldzx-codegenerator-maker";
        String tempDirPath = projectPath + File.separator +".temp";
        String templatePath = tempDirPath + File.separator + id;

        //是否为首次制作模板
        //目录不存在则是首次制作
        if(FileUtil.exist(templatePath)){
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originProjectPath,templatePath,true);
        }

        // 一.输入信息
        // 输入文件信息

        //要挖坑项目的根目录
        String sourceRootPath = templatePath + File.separator +FileUtil.getLastPathEle(Paths.get(originProjectPath)).toString();
        //win需要转义
        sourceRootPath = sourceRootPath.replaceAll("\\\\","/");
        List<TemplateMakerFileConfig.FileInfoConfig> fileConfigInfoList = templateMakerFileConfig.getFiles();

        // 二.生成文件模板
        List<Meta.FileConfigDTO.FilesInfo> newFileInfoList = new ArrayList<>();
        for (TemplateMakerFileConfig.FileInfoConfig fileInfoConfig : fileConfigInfoList) {
            String inputFilePath = fileInfoConfig.getPath();
            String inputFileAbsolutePath = sourceRootPath + File.separator + inputFilePath;
            //绝对路径
            if(!inputFilePath.startsWith(sourceRootPath)){
                inputFilePath = sourceRootPath + File.separator + inputFilePath;
                inputFilePath = inputFilePath.replaceAll("\\\\","/");
            }

            //获取过滤后的文件列表(不会存在目录)
        List<File> fileList = FileFilter.doFilter(inputFilePath,fileInfoConfig.getFileConfigList());
            for (File file : fileList) {
                Meta.FileConfigDTO.FilesInfo filesInfo = makeFileTemplate(templateMakerModelConfig,sourceRootPath,file);
                newFileInfoList.add(filesInfo);
            }

        }
        //如果是文件组
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = templateMakerFileConfig.getFileGroupConfig();
        if(fileGroupConfig != null){
            String condition = fileGroupConfig.getCondition();
            String groupKey = fileGroupConfig.getGroupKey();
            String groupName = fileGroupConfig.getGroupName();
            //新增分组配置
            Meta.FileConfigDTO.FilesInfo GroupFileInfo = new Meta.FileConfigDTO.FilesInfo();
            GroupFileInfo.setType(FileTypeEnum.GROUP.getValue());
            GroupFileInfo.setCondition(condition);
            GroupFileInfo.setGroupName(groupName);
            GroupFileInfo.setGroupKey(groupKey);
            //文件全放到一个分组内
            GroupFileInfo.setFiles(newFileInfoList);
            newFileInfoList = new ArrayList<>();
            newFileInfoList.add(GroupFileInfo);
        }
        //处理模型信息
        List<TemplateMakerModelConfig.ModelInfoConfig> models = templateMakerModelConfig.getModels();
        //.转换为配置接受的ModelInfo对象
        List<Meta.ModelConfigDTO.modelInfo> inputModelInfoList = models.stream().map(modelInfoConfig ->
        {   Meta.ModelConfigDTO.modelInfo modelInfo = new Meta.ModelConfigDTO.modelInfo();
            BeanUtil.copyProperties(modelInfoConfig,modelInfo);
            return modelInfo;
        }).collect(Collectors.toList());

        //本次新增的模型配置列表
        List<Meta.ModelConfigDTO.modelInfo> newModelInfoList = new ArrayList<>();

        //如果是模型组
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        if(modelGroupConfig != null){
            String condition = modelGroupConfig.getCondition();
            String groupKey = modelGroupConfig.getGroupKey();
            String groupName = modelGroupConfig.getGroupName();
            Meta.ModelConfigDTO.modelInfo groupModelInfo = new Meta.ModelConfigDTO.modelInfo();
            groupModelInfo.setCondition(condition);
            groupModelInfo.setGroupKey(groupKey);
            groupModelInfo.setGroupName(groupName);
            //模型全放到一个分组内
            groupModelInfo.setModels(inputModelInfoList);
            newModelInfoList.add(groupModelInfo);
        }else {
            //不分组,添加所有的模型信息到列表
            newModelInfoList.addAll(inputModelInfoList);
        }





        // 三. 生成配置文件
        String metaOutputPath = sourceRootPath + File.separator + "meta.json";
        //如果已有meta文件,说明不是第一次制作没在在meta上修改
        if(FileUtil.exist(metaOutputPath)){
            Meta oldMeTa = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath),Meta.class);
            BeanUtil.copyProperties(newMeta,oldMeTa, CopyOptions.create().ignoreNullValue());
            newMeta = oldMeTa;
            //1.追加配置参数
            List<Meta.FileConfigDTO.FilesInfo> filesInfoList = newMeta.getFileConfig().getFiles();
            filesInfoList.addAll(newFileInfoList);
            List<Meta.ModelConfigDTO.modelInfo> modelInfoList = newMeta.getModelConfig().getModels();
            modelInfoList.addAll(newModelInfoList);

            //配置去重
            newMeta.getFileConfig().setFiles(distinctFiles(filesInfoList));
            newMeta.getModelConfig().setModels(distinctModels(modelInfoList));
        }else {
            //1.追加配置参数
            Meta.FileConfigDTO fileConfigDTO = new Meta.FileConfigDTO();
            newMeta.setFileConfig(fileConfigDTO);
            fileConfigDTO.setSourceRootPath(sourceRootPath);
            List<Meta.FileConfigDTO.FilesInfo> filesInfoList = new ArrayList<>();
            fileConfigDTO.setFiles(filesInfoList);
            filesInfoList.addAll(newFileInfoList);

            Meta.ModelConfigDTO modelConfigDTO = new Meta.ModelConfigDTO();
            newMeta.setModelConfig(modelConfigDTO);
            List<Meta.ModelConfigDTO.modelInfo> modelInfoList = new ArrayList<>();
            modelConfigDTO.setModels(modelInfoList);
            modelInfoList.addAll(newModelInfoList);
        }
        //2.输出元信息文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta),metaOutputPath);
        return id;

    }

    private static Meta.FileConfigDTO.FilesInfo makeFileTemplate(TemplateMakerModelConfig templateMakerModelConfig,  String sourceRootPath,File inputFile) {
       //要挖坑的文件绝对路径
        String fileInputAbsolutePath = inputFile.getAbsolutePath();
        fileInputAbsolutePath = fileInputAbsolutePath.replaceAll("\\\\","/");

        String fileOutputAbsolutePath = fileInputAbsolutePath + ".ftl";

        //要挖坑的原文件
        //文件输入输出相对路径
        String fileInputPath = inputFile.getAbsolutePath().replace(sourceRootPath + "/","");
        String fileOutputPath = inputFile  +".ftl";

        // 二.使用字符串替换,生成模板文件
        String fileContent ;
        //如果以后模板文件,则说明不是第一次制作,在模板的基础上在次挖坑
        if(FileUtil.exist(fileOutputAbsolutePath)){
             fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        }else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }
        //支持多个模型:对同一个文件的内容,遍历模型进行多轮替换
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig =templateMakerModelConfig.getModelGroupConfig();
        String newFileContent = fileContent;
        String replacement;
        for (TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig : templateMakerModelConfig.getModels()){
            //不是分组
            if(modelInfoConfig == null){
                replacement = String.format("${%s}",modelInfoConfig.getFileName());
            }else {
                //是分组
                String groupKey = modelGroupConfig.getGroupKey();
                //注意挖坑要多一个层级
                replacement = String.format("${%s,%s}",groupKey,modelInfoConfig.getFileName());

            }
        }
        //文件配置信息
        Meta.FileConfigDTO.FilesInfo filesInfo = new Meta.FileConfigDTO.FilesInfo();
        filesInfo.setInputPath(fileInputPath);
        filesInfo.setOutputPath(fileOutputPath);
        filesInfo.setType(FileTypeEnum.File.getValue());

        //和源文件相同,静态生成
        if(newFileContent.equals(fileContent)){
            //输出路径=输入路径
            filesInfo.setOutputPath(fileInputPath);
            filesInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
        }else {
            //生成模板文件
            filesInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
            FileUtil.writeUtf8String(newFileContent,fileOutputAbsolutePath);
        }
        return filesInfo;

    }



    /**
     * 模型去重
     *
     * @param modelInfos
     * @return
     */

    private static List<Meta.ModelConfigDTO.modelInfo> distinctModels(List<Meta.ModelConfigDTO.modelInfo> modelInfos){
        //1.将所有文件分为有分组和无分组先处理无分组,以组单位进行划分
        Map<String,List<Meta.ModelConfigDTO.modelInfo>> groupKeyInfoListMap = modelInfos
                .stream()
                .filter(modelInfo -> StrUtil.isNotBlank(modelInfo.getGroupKey()))
                .collect(
                        Collectors.groupingBy(Meta.ModelConfigDTO.modelInfo::getGroupKey)
                );

        //2.同族内的文件配置合并
        //保存每个组对应的合并后的对象map
        Map<String,Meta.ModelConfigDTO.modelInfo> groupKeyMergedModelInfoMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.ModelConfigDTO.modelInfo>> entry : groupKeyInfoListMap.entrySet()) {
            List<Meta.ModelConfigDTO.modelInfo> tempModelInfoList = entry.getValue();
            List<Meta.ModelConfigDTO.modelInfo> newModelInfoList = new ArrayList<>(tempModelInfoList.stream()
                    .flatMap(modelInfo -> modelInfo.getModels().stream())
                    .collect(Collectors.toMap(Meta.ModelConfigDTO.modelInfo::getFieldName,o->o,(e,r) -> r)
                    ).values());
            //使用新的group的配置
            Meta.ModelConfigDTO.modelInfo newModelInfo = CollUtil.getLast(tempModelInfoList);
            newModelInfo.setModels(newModelInfoList);
            String groupKeY = entry.getKey();
            groupKeyMergedModelInfoMap.put(groupKeY,newModelInfo);
        }
        //3.将文件添加到结果列表
        List<Meta.ModelConfigDTO.modelInfo> resultList = new ArrayList<>(groupKeyMergedModelInfoMap.values());
        //4.将未分组的文件太牛加到结果列表
        List<Meta.ModelConfigDTO.modelInfo> noGroupFileInfoList = modelInfos.stream().filter(modelInfo -> StrUtil.isBlank(modelInfo.getGroupKey()))
                .collect(Collectors.toList());
        resultList.addAll(new ArrayList<>(noGroupFileInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.ModelConfigDTO.modelInfo::getFieldName,o->o,(e,r)-> r)
                ).values()));
        return resultList;


    }
    /**
     * 文件去重
     *
     * @param filesInfoList
     * @return
     */

    private static List<Meta.FileConfigDTO.FilesInfo> distinctFiles(List<Meta.FileConfigDTO.FilesInfo> filesInfoList){
        //1.将所有文件分为有分组和无分组先处理无分组,以组单位进行划分
        Map<String,List<Meta.FileConfigDTO.FilesInfo>> groupKeyInfoListMap = filesInfoList
                .stream()
                .filter(filesInfo -> StrUtil.isNotBlank(filesInfo.getGroupKey()))
                .collect(
                        Collectors.groupingBy(Meta.FileConfigDTO.FilesInfo::getGroupKey)
                );

        //2.同族内的文件配置合并
        //保存每个组对应的合并后的对象map
        Map<String,Meta.FileConfigDTO.FilesInfo> groupKeyMergedFileInfoMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.FileConfigDTO.FilesInfo>> entry : groupKeyInfoListMap.entrySet()) {
            List<Meta.FileConfigDTO.FilesInfo> tempFileInfoList = entry.getValue();
            List<Meta.FileConfigDTO.FilesInfo> newFileInfoList = new ArrayList<>(tempFileInfoList.stream()
                    .flatMap(filesInfo -> filesInfo.getFiles().stream())
                    .collect(Collectors.toMap(Meta.FileConfigDTO.FilesInfo::getInputPath,o->o,(e,r) -> r)
                    ).values());
            //使用新的group的配置
            Meta.FileConfigDTO.FilesInfo newFileInfo = CollUtil.getLast(tempFileInfoList);
            newFileInfo.setFiles(newFileInfoList);
            String groupKeY = entry.getKey();
            groupKeyMergedFileInfoMap.put(groupKeY,newFileInfo);
        }
        //3.将文件添加到结果列表
        List<Meta.FileConfigDTO.FilesInfo> resultList = new ArrayList<>(groupKeyMergedFileInfoMap.values());
        //4.将未分组的文件太牛加到结果列表
        List<Meta.FileConfigDTO.FilesInfo> noGroupFileInfoList = filesInfoList.stream().filter(filesInfo -> StrUtil.isBlank(filesInfo.getGroupKey()))
                .collect(Collectors.toList());
        resultList.addAll(new ArrayList<>(noGroupFileInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.FileConfigDTO.FilesInfo::getInputPath,o->o,(e,r)-> r)
                ).values()));
        return resultList;

    }


    public static void main(String[] args) {
        Meta meta = new Meta();
        meta.setName("acm-template-generator");
        meta.setDescription("ACM 示例模板生成器");

        String projectPath = System.getProperty("user.dir");
        String originProjectPath = projectPath + File.separator + "springboot-init";
        String InputFilePath1 = "src/main/java/com/yupi/springbootinit/common";
        String InputFilePath2 = "src/main/java/com/yupi/springbootinit/constant";

        //模型参数配置
        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        //1.模型组配置
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = new TemplateMakerModelConfig.ModelGroupConfig();
        modelGroupConfig.setGroupKey("mysql");
        modelGroupConfig.setGroupName("数据库配置");
        templateMakerModelConfig.setModelGroupConfig(modelGroupConfig);
        //2.模型配置
        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFileName("username");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setDefaultValue("root");
        modelInfoConfig1.setReplaceText("root");
        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig2 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig2.setFileName("url");
        modelInfoConfig2.setType("String");
        modelInfoConfig2.setDefaultValue("jdbc:mysql://localhost:3306/my_db");
        modelInfoConfig2.setReplaceText("jdbc:mysql://localhost:3306/my_db");
        List<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList =Arrays.asList(modelInfoConfig1,modelInfoConfig2);
        templateMakerModelConfig.setModels(modelInfoConfigList);



//        //模型参数信息(首次)
//        Meta.ModelConfigDTO.modelInfo modelInfo = new Meta.ModelConfigDTO.modelInfo();
//        modelInfo.setFieldName("outputText");
//        modelInfo.setType("String");
//        modelInfo.setDefaultValue("sum = ");

        //模型参数信息(第二次)
        Meta.ModelConfigDTO.modelInfo modelInfo = new Meta.ModelConfigDTO.modelInfo();
        modelInfo.setFieldName("className");
        modelInfo.setType("String");

//        //替换变量(首次)
//        String searchStr = "Sum: ";
        //  替换变量(第二次)
        String searchStr = "BaseResponse";
        //文件过滤
        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig1 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig1.setPath(InputFilePath1);
        List<FileFilterConfig> fileFilterConfigList = new ArrayList<>();
        FileFilterConfig fileFilterConfig = FileFilterConfig.builder()
                .range(FileFilterRangeEnum.File_NAME.getValue())
                .rule(FileFilterRuleEnum.CONTAINS.getValue())
                .value("Base")
                .build();
        //将赋值好的FileFilterConfig添加到数组中
        fileFilterConfigList.add(fileFilterConfig);

        fileInfoConfig1.setFileConfigList(fileFilterConfigList);
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig2 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig2.setPath(InputFilePath2);
        templateMakerFileConfig.setFiles(Arrays.asList(fileInfoConfig1,fileInfoConfig2));


        //分组配置
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = new TemplateMakerFileConfig.FileGroupConfig();
        fileGroupConfig.setCondition("outputText");
        fileGroupConfig.setGroupKey("test");
        fileGroupConfig.setGroupName("测试分组");
        templateMakerFileConfig.setFileGroupConfig(fileGroupConfig);


        long id = makeTemplate(meta, originProjectPath, templateMakerFileConfig, templateMakerModelConfig, 1800843306515238912l);
        System.out.println(id);


    }
}