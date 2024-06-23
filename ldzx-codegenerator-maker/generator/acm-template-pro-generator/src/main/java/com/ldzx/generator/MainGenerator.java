package com.ldzx.generator;

import com.ldzx.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;



    /**
    * 核心生成器
    */
    public class MainGenerator {

    /**
    * 生成
    *
    * @param model 数据模型
    * @throws TemplateException
    * @throws IOException
    */

    public static void doGenerate(DataModel model) throws TemplateException, IOException {
        String inputRootPath = ".source/acm-template-pro";
        String outputRootPath = "generated";

        String inputPath;
        String outputPath;

        boolean needGit = model.needGit;
        boolean loop = model.loop;
        String author = model.author;
        String outputTest = model.outputTest;

        // groupKey = git
        if (needGit) {
            inputPath = new File(inputRootPath, ".gitignore").getAbsolutePath();
            outputPath = new File(outputRootPath, ".gitignore").getAbsolutePath();
            StaticGenerator.copyFilesByHutool(inputPath, outputPath);
            inputPath = new File(inputRootPath, "README.md").getAbsolutePath();
            outputPath = new File(outputRootPath, "README.md").getAbsolutePath();
            StaticGenerator.copyFilesByHutool(inputPath, outputPath);
            }
        inputPath = new File(inputRootPath, "src/com/ldzx/acm/MainTemplate.java.ftl").getAbsolutePath();
        outputPath = new File(outputRootPath, "src/com/ldzx/acm/MainTemplate.java").getAbsolutePath();
        DynamicGenerator.doGenerate(inputPath, outputPath, model);
}
}