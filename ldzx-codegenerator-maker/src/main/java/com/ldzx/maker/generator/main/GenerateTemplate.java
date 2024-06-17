package com.ldzx.maker.generator.main;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ldzx.maker.generator.JarGenerator;
import com.ldzx.maker.generator.ScripGenerator;
import com.ldzx.maker.generator.file.DynamicFileGenerator;
import com.ldzx.maker.meta.Meta;
import com.ldzx.maker.meta.Metamanger;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public abstract class GenerateTemplate {
    public void doGenerate() throws TemplateException, IOException, InterruptedException {
        Meta meta = Metamanger.getMetaObject();
        //0.输出根路径
        String projectPath = System.getProperty("user.dir") + File.separator + "ldzx-codegenerator-maker";

        String outputPath = projectPath + File.separator + "generator" + File.separator + meta.getName();
        if(!FileUtil.exist(outputPath)){
            FileUtil.mkdir(outputPath);
        }

        //1.复制原始文件
        String sourceCopyDestPath = copySource(meta, outputPath);

        // 2、代码生成
        generatecode(projectPath, meta, outputPath);

        // 3.构建 jar 包
        JarGenerator.doGenerate(outputPath);

        // 4.封装脚本
        String shellOutputFilePath = outputPath + File.separator + "generator";
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        ScripGenerator.doGenerate(shellOutputFilePath, jarPath);


        // 5.生成精简版
        buildDist(outputPath, jarPath, shellOutputFilePath, sourceCopyDestPath);


    }

    public  String copySource(Meta meta, String outputPath) {
        String sourceRootPath = meta.getFileConfig().getSourceRootPath();
        String sourceCopyDestPath = outputPath + File.separator + ".source";
        FileUtil.copy(sourceRootPath,sourceCopyDestPath,false);
        return sourceCopyDestPath;
    }

    public  void buildDist(String outputPath, String jarPath, String shellOutputFilePath, String sourceCopyDestPath) {
        String disOutputPath = outputPath + "-dist";
        // 拷贝jar包
        String targetAbsoultePath = disOutputPath + File.separator + "target";
        FileUtil.mkdir(targetAbsoultePath);
        String jarAbsoultePath = outputPath + File.separator + jarPath;
        FileUtil.copy(jarAbsoultePath,targetAbsoultePath,true);
        //- 拷贝脚本文件
        FileUtil.copy(shellOutputFilePath,disOutputPath,true);
        FileUtil.copy(shellOutputFilePath + ".bat",disOutputPath,true);
        //- 拷贝源模板文件
        FileUtil.copy(sourceCopyDestPath,disOutputPath,true);
    }

    public  void generatecode(String projectPath, Meta meta, String outputPath) throws IOException, TemplateException {
        //读取resources目录
        String inputResourcePath = projectPath + File.separator + "src/main/resources";

        //java包基本路径
        // Java 包基础路径
        String outputBasePackage = meta.getBasePackage();
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage, "."));
        String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

        String inputFilePath;
        String outputFilePath;

        // model.DataModel
        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/model/DataModel.java";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);

        // cli.command.ConfigCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ConfigCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);

        // cli.command.GenerateCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/GenerateCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);

        // cli.command.ListCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ListCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);

        // cli.CommandExecutor
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/CommandExecutor.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/CommandExecutor.java";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);

        // Main
        inputFilePath = inputResourcePath + File.separator + "templates/java/Main.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/Main.java";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);

        // generator.DynamicGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/DynamicGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/DynamicGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);

        // generator.MainGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/MainGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/MainGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);

        // generator.StaticGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/StaticGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/StaticGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);

        // pom.xml
        inputFilePath = inputResourcePath + File.separator + "templates/pom.xml.ftl";
        outputFilePath = outputPath + File.separator + "pom.xml";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);

        //README.md
        inputFilePath = inputResourcePath + File.separator + "templates/README.md.ftl";
        outputFilePath = outputPath + File.separator + "README.md";
        DynamicFileGenerator.doGenerator(inputFilePath , outputFilePath, meta);
    }
}
