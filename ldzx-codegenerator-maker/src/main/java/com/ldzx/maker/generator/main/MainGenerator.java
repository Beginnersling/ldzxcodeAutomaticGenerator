package com.ldzx.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.ldzx.maker.generator.file.DynamicFileGenerator;
import com.ldzx.maker.meta.Meta;
import com.ldzx.maker.meta.Metamanger;
import com.sun.org.apache.bcel.internal.classfile.Code;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {
    public static void main(String[] args) throws TemplateException, IOException {
        Meta meta = Metamanger.getMetaObject();
        //输出根路径
        String projectPath = System.getProperty("user.dir") + File.separator + "ldzx-codegenerator-maker";
        String outputPath = projectPath + File.separator + "generator" + File.separator + meta.getName();
        if(!FileUtil.exist(outputPath)){
            FileUtil.mkdir(outputPath);
        }
        //读取resources目录

        String inputResourcePath = projectPath + File.separator + "src\\main\\resources";

        //java包基本路径
        // Java 包基础路径
        String outputBasePackage = meta.getBasePackage();
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage, "."));
        String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

        String inputFilePath;
        String outputFilePath;


        //model.DataModel
        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath =outputBaseJavaPackagePath + "/model/DataModel.java";
        DynamicFileGenerator.doGenerator(inputFilePath,outputFilePath,meta);
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

    }
}
