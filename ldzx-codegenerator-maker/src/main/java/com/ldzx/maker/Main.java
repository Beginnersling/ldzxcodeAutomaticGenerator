package com.ldzx.maker;

import com.ldzx.maker.ctl.CommandExecutor;

public class Main {
    public static void main(String[] args) {

        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExcute(args);
    }
}