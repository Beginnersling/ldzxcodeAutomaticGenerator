
package com.ldzx.maker.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

public class TemplateMakerModelConfig {
    private List<ModelInfoConfig> models;
    private ModelGroupConfig modelGroupConfig;
    @Data
    @NoArgsConstructor
    public static class ModelInfoConfig{
        private String fieldName;
        private String type;
        private String description;
        private Object defaultValue;
        private String addr;
        private String replaceText;
    }
    @Data public static class ModelGroupConfig{
        private String condition;
        private String groupKey;
        private String groupName;
        private String type;
        private String description;

    }
}
