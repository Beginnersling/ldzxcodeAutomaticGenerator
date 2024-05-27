package com.ldzx.maker.ctl.pattern;

public class Client {
    public static void main(String[] args) {
        //创建接收者对象
        Device tv = new Device("tv");
        Device shy = new Device("shy");
        //创建命令对象绑定不同设备
        TurnOffCommand turnOffCommand = new TurnOffCommand(tv);
        TurnOnCommand turnOnCommand = new TurnOnCommand(shy);
        //创建调用者
        RemoteControl remoteControl = new RemoteControl();
        remoteControl.setCommand(turnOffCommand);
        remoteControl.pressButton();
        remoteControl.setCommand(turnOnCommand);
        remoteControl.pressButton();


    }
}
