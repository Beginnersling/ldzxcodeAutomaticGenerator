package com.ldzx.maker.model;

import lombok.Data;

/*
* 对于示例代码需要抽取的变量进行抽取
* */
@Data
public class DataModel {
    /*
    * 作者
    * */
    private String author;
    /*
    * 是否循环
    * */
    private boolean loop;

    /*
    * 输出信息
    * */
    private String outputTest ="sum=";
    


}
