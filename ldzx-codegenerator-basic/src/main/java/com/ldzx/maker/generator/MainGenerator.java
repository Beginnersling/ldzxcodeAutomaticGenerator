package com.ldzx.maker.generator;

import com.ldzx.maker.model.MainTemplateComfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {
    public static void DoGenerator(Object model) throws TemplateException, IOException {
        //项目根目录
        String projectPath = System.getProperty("user.dir");
        //输入路径
        String inputPath = projectPath + File.separator +"ldzx-generator-demo-projects" + File.separator +"acm-template";

        //输出路径
        String outputPath = projectPath;
        //生成静态文件
        StaticGenerator.copyFileByHutool(inputPath,outputPath);
        //生成动态文件
        String DenamicinputPath = projectPath + File.separator + "ldzx-codegenerator-basic" +  File.separator +"src/main/resources/templates";
        String DenamicoutputPath =outputPath + File.separator+ "MainTemplate.java";
        DenamicGenerator.doGenerator(DenamicinputPath,DenamicoutputPath,model);


    }

    public static void main(String[] args) throws TemplateException, IOException {
        MainTemplateComfig mainTemplateComfig = new MainTemplateComfig();
        mainTemplateComfig.setAuthor("ldzx");
        mainTemplateComfig.setLoop(false);
        mainTemplateComfig.setOutputTest("求和结果:");
        DoGenerator(mainTemplateComfig);
    }
}
