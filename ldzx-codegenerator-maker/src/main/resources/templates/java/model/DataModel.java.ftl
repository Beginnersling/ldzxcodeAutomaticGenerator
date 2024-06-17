package ${basePackage}.model;

import lombok.Data;

/*
* 对于示例代码需要抽取的变量进行抽取
* */
@Data
public class DataModel {
<#list modelConfig.models as modelInfo>
    <#if modelInfo.description??>
        /**
        * ${modelInfo.description}
        */
    </#if>
    public ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c}</#if>;
</#list>
    }
