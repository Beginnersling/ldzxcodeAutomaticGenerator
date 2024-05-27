package com.ldzx.maker.ctl.pattern;

public class TurnOnCommand implements Command{
    private Device device;
    public TurnOnCommand(Device device){
        this.device = device;
    }
    @Override
    public void excute() {
        device.turnOn();
    }
}
