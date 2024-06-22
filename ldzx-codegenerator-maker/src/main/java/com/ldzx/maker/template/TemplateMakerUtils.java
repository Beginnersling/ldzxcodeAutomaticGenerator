package com.ldzx.maker.template;

import cn.hutool.core.util.StrUtil;
import com.ldzx.maker.meta.Meta;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//todo
/**
 * 模板制作工具
 */
public class TemplateMakerUtils {
    /**
     * 从为分组文件中溢出组内的同命文件
     * @param fileInfoList
     * @return
     */
    public static List<Meta.FileConfig.FileInfo> removeGroupFilesFromRoot(List<Meta.FileConfig.FileInfo> fileInfoList){
        //先获取所有分组
        List<Meta.FileConfig.FileInfo> groupFileInfoList = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                .collect(Collectors.toList());


        //获取所有分组内的文件列表
        List<Meta.FileConfig.FileInfo> groupInnerFilefoList = groupFileInfoList.stream()
                .flatMap(fileInfo -> fileInfo.getFiles().stream())
                .collect(Collectors.toList());

        //获取所有分组内输入路径集合
        Set<String> fileInputPathSet = groupInnerFilefoList.stream()
                .map(Meta.FileConfig.FileInfo::getInputPath)
                .collect(Collectors.toSet());

        //移除所有名称在set中的外层文件
        return fileInfoList.stream()
                .filter(fileInfo -> !fileInputPathSet.contains(fileInfo.getInputPath()))
                .collect(Collectors.toList());







    }
}
