package com.ldzx.maker.ctl.pattern;

public class TurnOffCommand implements Command{
    private Device device;
    public TurnOffCommand(Device device){
        this.device = device;
    }
    @Override
    public void excute() {
        device.turnOff();
    }
}
