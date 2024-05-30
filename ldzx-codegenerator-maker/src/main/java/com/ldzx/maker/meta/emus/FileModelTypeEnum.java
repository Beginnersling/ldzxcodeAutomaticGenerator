package com.ldzx.maker.meta.emus;

public enum FileModelTypeEnum {
    STRING("字符串","String"),
    BOOLEAN("布尔","boolean");
    private final String text;
    private final String value;

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }



    FileModelTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }






}
