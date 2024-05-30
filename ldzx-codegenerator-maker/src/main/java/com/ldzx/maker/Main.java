package com.ldzx.maker;


import com.ldzx.maker.generator.main.MainGenerator;
import com.ldzx.maker.meta.emus.FileModelTypeEnum;
import freemarker.template.TemplateException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        MainGenerator mainGenerator = new MainGenerator();
        mainGenerator.doGenerate();


    }
}
