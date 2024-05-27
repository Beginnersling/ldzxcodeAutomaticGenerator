package ${basePackage} .model;

import lombok.Data;

/*
* 对于示例代码需要抽取的变量进行抽取
* */
@Data
public class DataModel {
<#list modelConfig.models as modelInfo>
    <#if modelInfo.description??>

    </#if>

</#list>
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
