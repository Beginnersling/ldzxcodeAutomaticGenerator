package com.ldzx.maker.generator.main;

import com.ldzx.maker.meta.Meta;
import com.ldzx.maker.meta.Metamanger;

public class MainGenerator {
    public static void main(String[] args) {
        Meta meta = Metamanger.getMetaObject();
        System.out.println(meta);
    }
}
