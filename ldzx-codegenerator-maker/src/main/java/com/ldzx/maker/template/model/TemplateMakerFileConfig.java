package com.ldzx.maker.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

public class TemplateMakerFileConfig {
    private List<FileInfoConfig> files;
    private FileGroupConfig fileGroupConfig;
    @Data
    @NoArgsConstructor
    public static class FileInfoConfig{
        private String path;
        private String condition;
        public List<FileFilterConfig> fileConfigList;
    }
    @Data
    public static class FileGroupConfig{
        private String condition;
        private String groupKey;
        private String groupName;

    }
}
