package com.ldzx.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

public class Metamanger {
    private static volatile Meta meta;
    private Metamanger(){
        //放置外部实例化
    }

    public static Meta getMetaObject(){
        if(meta == null){
            synchronized (Metamanger.class){
                if(meta == null){
                    meta = initMate();
                }
            }
        }
        return meta;
    }
    private static Meta initMate(){
        String metaJson = ResourceUtil.readUtf8Str("meta.json");
        Meta newMate = JSONUtil.toBean(metaJson, Meta.class);
        Meta.FileConfig fileConfig = newMate.getFileConfig();
        MetaValidator.doValidatorAndFill(newMate);
        return newMate;

    }


}
