package com.ldzx.maker.template.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

@Getter
public enum FileFilterRangeEnum {
    File_NAME("文件名称","fileName"),
    File_CONTENT("文件内容","fileContent");

    private final String text;
    private final String value;

    FileFilterRangeEnum(String text,String value){
        this.text = text;
        this.value = value;

    }
    public static FileFilterRangeEnum getEnumByValue(String value){
        if(ObjectUtil.isEmpty(value)){
            return null ;
        }
        for (FileFilterRangeEnum anEnum : FileFilterRangeEnum.values()){
            if(anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }

}
