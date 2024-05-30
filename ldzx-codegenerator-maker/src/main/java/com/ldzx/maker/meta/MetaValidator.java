package com.ldzx.maker.meta;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ldzx.maker.meta.emus.FileGenerateTypeEnum;
import com.ldzx.maker.meta.emus.FileModelTypeEnum;
import com.ldzx.maker.meta.emus.FileTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class MetaValidator {


    public static void doValidatorAndFill(Meta meta){

        validAndFillMetaRoot(meta);
        vaildAndFillFileConfig(meta);
        vaildAndModelConfig(meta);


    }

    private static void vaildAndModelConfig(Meta meta) {
        //modelConfig校验
        Meta.ModelConfigDTO modelConfig = meta.getModelConfig();

        if(modelConfig != null){
            return;
        }
            List<Meta.ModelConfigDTO.modelInfo> modelInfoList = meta.getModelConfig().getModels();
            if(CollectionUtil.isNotEmpty(modelInfoList)){
                return;
            }
                for(Meta.ModelConfigDTO.modelInfo modelConfigDTO : modelInfoList){
                    //输出路径默认值
                    String fieldName = modelConfigDTO.getFieldName();
                    if(StrUtil.isBlank(fieldName)){
                        throw new MetaException("未填写 fieldName");
                    }
                    String type = modelConfigDTO.getType();
                    if(StrUtil.isEmpty(type)){
                        modelConfigDTO.setType(FileModelTypeEnum.STRING.getValue());
                    }
                }


    }

    private static void vaildAndFillFileConfig(Meta meta) {
        //FileConfig校验
        Meta.FileConfigDTO fileConfig = meta.getFileConfig();
        if(fileConfig != null){

            //sourceRootPath
            String sourceRootPath = fileConfig.getSourceRootPath();
            if(StrUtil.isBlank(sourceRootPath)){
                throw new MetaException("未填写 sourceRootPath");
            }
            // inputRootPath：.source + sourceRootPath 的最后一个层级路径
            String inputRootPath = fileConfig.getInputRootPath();
            String defaultInputRootPath = ".source" + File.separator + FileUtil.getLastPathEle(Paths.get(sourceRootPath)).getFileName().toString();
            if (StrUtil.isEmpty(inputRootPath)) {
                fileConfig.setInputRootPath(defaultInputRootPath);
            }
            // outputRootPath：默认为当前路径下的 generated
            String outputRootPath = fileConfig.getOutputRootPath();
            String defaultOutputRootPath = "generated";
            if (StrUtil.isEmpty(outputRootPath)) {
                fileConfig.setOutputRootPath(defaultOutputRootPath);
            }
            String fileConfigType = fileConfig.getType();
            String defaultType = FileTypeEnum.DIR.getValue();
            if (StrUtil.isEmpty(fileConfigType)) {
                fileConfig.setType(defaultType);
            }

        }
        // fileInfo 默认值
        List<Meta.FileConfigDTO.FilesInfo> fileInfoList = fileConfig.getFiles();
        if (!CollectionUtil.isNotEmpty(fileInfoList)) {
            return;
        }
        for (Meta.FileConfigDTO.FilesInfo fileInfo : fileInfoList) {
            // inputPath: 必填
            String inputPath = fileInfo.getInputPath();
            if (StrUtil.isBlank(inputPath)) {
                throw new MetaException("未填写 inputPath");
            }

            // outputPath: 默认等于 inputPath
            String outputPath = fileInfo.getOutputPath();
            if (StrUtil.isEmpty(outputPath)) {
                fileInfo.setOutputPath(inputPath);
            }
            // type：默认 inputPath 有文件后缀（如 .java）为 file，否则为 dir
            String type = fileInfo.getType();
            if (StrUtil.isBlank(type)) {
                // 无文件后缀
                if (StrUtil.isBlank(FileUtil.getSuffix(inputPath))) {
                    fileInfo.setType(FileTypeEnum.DIR.getValue());
                } else {
                    fileInfo.setType(FileTypeEnum.File.getValue());
                }
            }
            // generateType：如果文件结尾不为 Ftl，generateType 默认为 static，否则为 dynamic
            String generateType = fileInfo.getGenerateType();
            if (StrUtil.isBlank(generateType)) {
                // 为动态模板
                if (inputPath.endsWith(".ftl")) {
                    fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
                } else {
                    fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
                }
            }
        }
    }

    private static void validAndFillMetaRoot(Meta meta) {
        //基础信息校验
        String name = meta.getName();
        if(StrUtil.isBlank(name)){
            name = "my-generator";
            meta.setName(name);
        }

        String description = meta.getDescription();
        if(StrUtil.isEmpty(description)){
            description = "我的模板生成器";
            meta.setDescription(description);
        }
        String basePackage = meta.getBasePackage();
        if(StrUtil.isBlank(basePackage)){
            basePackage = "com.ldzx";
            meta.setBasePackage(basePackage);
        }
        String version = meta.getVersion();
        if(StrUtil.isEmpty(version)){
            version = "1.0";
            meta.setBasePackage(version);
        }
        String author = meta.getAuthor();
        if(StrUtil.isEmpty(author)){
            author = "ldzx";
            meta.setBasePackage(author);
        }
        String createTime = meta.getCreateTime();
        if(StrUtil.isBlank(createTime)){
            createTime = DateUtil.now();
            meta.setBasePackage(createTime);
        }
    }


}
