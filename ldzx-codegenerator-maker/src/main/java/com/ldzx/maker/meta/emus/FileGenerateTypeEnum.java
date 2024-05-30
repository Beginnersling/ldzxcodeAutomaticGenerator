package com.ldzx.maker.meta.emus;

public enum FileGenerateTypeEnum {
    DYNAMIC("动态","dynamic"),
    STATIC("静态","static");
    private final String text;
    private final String value;

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }



    FileGenerateTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }






}
