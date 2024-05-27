package com.ldzx.maker.ctl.command;

import cn.hutool.core.util.ReflectUtil;
import com.ldzx.maker.model.DataModel;
import picocli.CommandLine;

import java.lang.reflect.Field;

@CommandLine.Command(name = "config",description = "查看参数信息" ,mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable{
    @Override
    public void run() {

        System.out.println("查看参数信息");
        Field[] field = ReflectUtil.getFields(DataModel.class);
        for (Field field1 : field) {
            System.out.println("字段名称" + field1.getName());
            System.out.println("字段类型" + field1.getType());
        }


    }
}
