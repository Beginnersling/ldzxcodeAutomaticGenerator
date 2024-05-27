package com.ldzx.maker.generator.file;

import cn.hutool.core.io.FileUtil;

public class StaticFileGenerator {


    public static void copyFileByHutool(String inputPath,String outputPath){
        FileUtil.copy(inputPath,outputPath,false);
    }
}
