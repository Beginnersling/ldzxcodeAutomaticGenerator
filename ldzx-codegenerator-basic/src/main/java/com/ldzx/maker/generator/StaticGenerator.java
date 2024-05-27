package com.ldzx.maker.generator;

import cn.hutool.core.io.FileUtil;

import java.io.File;

public class StaticGenerator {

    public static void main(String[] args) {
        String ProjectPath = System.getProperty("user.dir");

        //输入路径
        System.out.println(ProjectPath);
        String inputPath = ProjectPath + File.separator +"ldzx-generator-demo-projects" + File.separator +"acm-template";
        String outputPath = ProjectPath;
        copyFileByHutool(inputPath,outputPath);



    }


    public static void copyFileByHutool(String inputPath,String outputPath){
        FileUtil.copy(inputPath,outputPath,false);
    }
}
