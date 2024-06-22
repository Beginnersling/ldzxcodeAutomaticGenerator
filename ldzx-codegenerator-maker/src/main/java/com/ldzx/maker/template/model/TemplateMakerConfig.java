package com.ldzx.maker.template.model;

import com.ldzx.maker.meta.Meta;
import com.ldzx.maker.template.TemplateMakerOutputConfig;
import lombok.Data;

/**
 * 模板制作配置
 */
@Data
public class TemplateMakerConfig {
    private long id;
    private Meta meta = new Meta();
    private String originProjectPath;
    TemplateMakerFileConfig fileConfig = new TemplateMakerFileConfig();
    TemplateMakerModelConfig modelConfig = new TemplateMakerModelConfig();
    TemplateMakerOutputConfig outputConfig = new TemplateMakerOutputConfig();


}
