package com.ldzx.maker.meta.emus;

public enum FileTypeEnum {
    DIR("目录","dir"),
    File("文件","file"),
    GROUP("文件组","group");
    private final String text;
    private final String value;


    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }



    FileTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }






}
