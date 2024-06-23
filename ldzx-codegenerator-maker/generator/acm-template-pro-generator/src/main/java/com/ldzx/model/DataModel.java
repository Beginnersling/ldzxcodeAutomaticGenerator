package com.ldzx.model;

import lombok.Data;

/*
* 对于示例代码需要抽取的变量进行抽取
* */
@Data
public class DataModel {
        /**
        * 是否生成.gitignore README文件
        */
    public boolean needGit = true;
        /**
        * 是否生成循环
        */
    public boolean loop = false;
        /**
        * 用于生成核心模板文件
        */
    public MainTemplate FreeMarker template error (DEBUG mode; use RETHROW in production!):
The following has evaluated to null or missing:
==> modelInfo.fieldName  [in template "DataModel.java.ftl" at line 16, column 32]

----
Tip: It's the step after the last dot that caused this error, not those before it.
----
Tip: If the failing expression is known to legally refer to something that's sometimes null or missing, either specify a default value like myOptionalVar!myDefault, or use <#if myOptionalVar??>when-present<#else>when-missing</#if>. (These only cover the last step of the expression; to cover the whole expression, use parenthesis: (myOptionalVar.foo)!myDefault, (myOptionalVar.foo)??
----

----
FTL stack trace ("~" means nesting-related):
	- Failed at: ${modelInfo.fieldName}  [in template "DataModel.java.ftl" at line 16, column 30]
----

Java stack trace (for programmers):
----
freemarker.core.InvalidReferenceException: [... Exception message was already printed; see it above ...]
	at freemarker.core.InvalidReferenceException.getInstance(InvalidReferenceException.java:134)
	at freemarker.core.EvalUtil.coerceModelToTextualCommon(EvalUtil.java:481)
	at freemarker.core.EvalUtil.coerceModelToStringOrMarkup(EvalUtil.java:401)
	at freemarker.core.EvalUtil.coerceModelToStringOrMarkup(EvalUtil.java:370)
	at freemarker.core.DollarVariable.calculateInterpolatedStringOrMarkup(DollarVariable.java:104)
	at freemarker.core.DollarVariable.accept(DollarVariable.java:63)
	at freemarker.core.Environment.visit(Environment.java:371)
	at freemarker.core.IteratorBlock$IterationContext.executedNestedContentForCollOrSeqListing(IteratorBlock.java:321)
	at freemarker.core.IteratorBlock$IterationContext.executeNestedContent(IteratorBlock.java:271)
	at freemarker.core.IteratorBlock$IterationContext.accept(IteratorBlock.java:244)
	at freemarker.core.Environment.visitIteratorBlock(Environment.java:645)
	at freemarker.core.IteratorBlock.acceptWithResult(IteratorBlock.java:108)
	at freemarker.core.IteratorBlock.accept(IteratorBlock.java:94)
	at freemarker.core.Environment.visit(Environment.java:335)
	at freemarker.core.Environment.visit(Environment.java:341)
	at freemarker.core.Environment.process(Environment.java:314)
	at freemarker.template.Template.process(Template.java:383)
	at com.ldzx.maker.generator.file.DynamicFileGenerator.doGenerator(DynamicFileGenerator.java:34)
	at com.ldzx.maker.generator.main.GenerateTemplate.generatecode(GenerateTemplate.java:86)
	at com.ldzx.maker.generator.main.GenerateTemplate.doGenerate(GenerateTemplate.java:31)
	at com.ldzx.maker.Main.main(Main.java:13)
