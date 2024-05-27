package com.ldzx.maker.ctl.command;

import cn.hutool.core.bean.BeanUtil;
import com.ldzx.maker.model.DataModel;
import com.ldzx.maker.generator.file.MainFileGenerator;
import lombok.Data;
import picocli.CommandLine;

import java.util.concurrent.Callable;
@Data
@CommandLine.Command(name = "generate",description = "生成代码" ,mixinStandardHelpOptions = true)
public class GengerateCommand implements Callable<Integer> {
    @CommandLine.Option(names = {"-a","--author"},description = {"作者"},arity = "0..1",interactive = true)
    private String author = "ldzx";
    /*
     * 是否循环
     * */
    @CommandLine.Option(names = {"-l","--loop"},description = {"是否循环"},arity = "0..1",interactive = true)
    private boolean loop;

    /*
     * 输出信息
     * */
    @CommandLine.Option(names = {"-o","--outputTest"},description = {"输出信息"},arity = "0..1",interactive = true)
    private String outputTest = "sum= ";

    @Override
    public Integer call() throws Exception {
        DataModel mainTemplateComfig = new DataModel();
        BeanUtil.copyProperties(this,mainTemplateComfig);
        System.out.println("配置信息:" + mainTemplateComfig);
        MainFileGenerator.DoGenerator(mainTemplateComfig);
        return 0;
    }
}
