package ${basePackage}.ctl.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.model.DataModel;
import ${basePackage}.generator.file.MainFileGenerator;
import lombok.Data;
import picocli.CommandLine;
import java.util.concurrent.Callable;
@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {
    <#list modelConfig.models as modelInfo>

        @Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}", </#if>"--${modelInfo.fieldName}"}, arity = "0..1", <#if modelInfo.description??>description = "${modelInfo.description}", </#if>interactive = true, echo = true)
        private ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c}</#if>;
    </#list>

public Integer call() throws Exception {
    DataModel dataModel = new DataModel();
    BeanUtil.copyProperties(this, dataModel);
    MainGenerator.doGenerate(dataModel);
    return 0;
    }

}