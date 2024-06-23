package com.ldzx.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.ldzx.generator.MainGenerator;
import com.ldzx.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {

        @Option(names = {"-l", "--needGit"}, arity = "0..1", description = "是否生成.gitignore README文件", interactive = true, echo = true)
        private boolean needGit = false;

        @Option(names = {"-l", "--loop"}, arity = "0..1", description = "是否生成循环", interactive = true, echo = true)
        private boolean loop = false;

        @Option(names = {"-a", "--author"}, arity = "0..1", description = "作者注释", interactive = true, echo = true)
        private String author = "ldzx";

        @Option(names = {"-o", "--outputTest"}, arity = "0..1", description = "输出信息", interactive = true, echo = true)
        private String outputTest = "sum = ";

    public Integer call() throws Exception {
    DataModel dataModel = new DataModel();
    BeanUtil.copyProperties(this, dataModel);
    MainGenerator.doGenerate(dataModel);
    return 0;
    }
    }