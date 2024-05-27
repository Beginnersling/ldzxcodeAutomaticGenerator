package com.ldzx.maker.ctl.example;

import picocli.CommandLine;

@CommandLine.Command(name = "ASSCIIArt",version = "ASSCIIArt1.0",mixinStandardHelpOptions = true)
public class ASSCIIArt implements Runnable{
@CommandLine.Option(names = {"-u","--uzi"},description = {"champion"})
    int uzi = 1;
@CommandLine.Parameters(paramLabel = "<word>",defaultValue = "Hello,pocicli",
                        description = "uzi is champion")
private String[] words ={"Hello","Champion"};
    @Override
    public void run() {
        System.out.println("uzi" + uzi);
        System.out.println("words=" + String.join(",",words) );

    }

    public static void main(String[] args) {
        int a = 0;
        int exitCpde = new CommandLine(new ASSCIIArt()).execute(args);
        System.exit(exitCpde);
    }

}
