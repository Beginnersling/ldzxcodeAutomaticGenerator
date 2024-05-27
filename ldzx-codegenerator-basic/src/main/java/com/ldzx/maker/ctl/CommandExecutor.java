package com.ldzx.maker.ctl;

import com.ldzx.maker.ctl.command.ConfigCommand;
import com.ldzx.maker.ctl.command.GengerateCommand;
import com.ldzx.maker.ctl.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "ldzx" , mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {
    private final CommandLine commandLine;
    {
        commandLine = new CommandLine(this)
                .addSubcommand(new GengerateCommand())
                .addSubcommand(new ListCommand())
                .addSubcommand(new ConfigCommand());
    }


    @Override
    public void run() {
        System.out.println("请输入具体指令或者使用--help帮助");
    }

    public Integer doExcute(String[] args){
        return commandLine.execute(args);
    }

}
