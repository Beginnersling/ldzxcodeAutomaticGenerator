package com.ldzx.maker.generator;

import com.ldzx.maker.model.MainTemplateComfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


public class DenamicGenerator {
    public static void main(String[] args) throws IOException, TemplateException {

    // Specify the source where the template files come from. Here I set a
    // plain directory for it, but non-file-system sources are possible too:
        String projectPath = System.getProperty("user.dir") + File.separator + "ldzx-codegenerator-basic";
        String inputPath = projectPath + File.separator + "src/main/resources/templates";
        String outputString = projectPath + File.separator +"MainTemplate.java";
        MainTemplateComfig mainTemplateComfig = new MainTemplateComfig();
        mainTemplateComfig.setAuthor("ldzx");
        mainTemplateComfig.setLoop(false);
        mainTemplateComfig.setOutputTest("求和结果:");

        doGenerator(inputPath,outputString,mainTemplateComfig);





    }

    public static void doGenerator(String inputPath,String outputPath,Object model) throws IOException, TemplateException {
        // Create your Configuration instance, and specify if up to what FreeMarker
        // version (here 2.3.32) do you want to apply the fixes that are not 100%
        // backward-compatible. See the Configuration JavaDoc for details.
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File(inputPath));
        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");
        // 创建模板对象,加载指定模板
        Template template = cfg.getTemplate("MainTemplate.java.ftl");
        //创建数据模型
        MainTemplateComfig mainTemplateComfig = new MainTemplateComfig();
        mainTemplateComfig.setAuthor("ldzx");
        mainTemplateComfig.setLoop(false);
        mainTemplateComfig.setOutputTest("求和结果:");

        //生成
        Writer out = new  FileWriter(outputPath);
        template.process(mainTemplateComfig,out);
        //结束线程
        out.close();

    }
}
