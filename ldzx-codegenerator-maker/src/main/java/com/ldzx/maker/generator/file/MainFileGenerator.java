package com.ldzx.maker.generator.file;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;

public class MainFileGenerator {
    public static void DoGenerator(Object model) throws TemplateException, IOException {
        //项目根目录
        String projectPath = System.getProperty("user.dir") + File.separator + "ldzx-codegenerator-maker";
        //输入路径
        String inputPath = projectPath + File.separator +"ldzx-generator-demo-projects" + File.separator +"acm-template-pro";

        //输出路径
        String outputPath = projectPath;
        //生成静态文件
        StaticFileGenerator.copyFileByHutool(inputPath,outputPath);
        //生成动态文件
        String DynamicinputPath = projectPath + File.separator + "ldzx-codegenerator-maker" +  File.separator +"src/main/resources/templates";
        String DynamicoutputPath =outputPath + File.separator+ "MainTemplate.java";
        DynamicFileGenerator.doGenerator(DynamicinputPath,DynamicoutputPath,model);


    }


}
