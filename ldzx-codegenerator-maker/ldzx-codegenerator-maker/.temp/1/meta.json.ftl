{
    "name": "springboot-init-generator",
    "description": "Spring Boot 模板项目生成器",
    "fileConfig": {
        "sourceRootPath": "D:/AWorkspace/Code/Project1/ldzx-CodeGenerator/ldzx-codegenerator-maker/ldzx-codegenerator-maker/.temp/1/springboot-init",
        "files": [
            {
                "type": "group",
                "condition": "needPost",
                "groupName": "帖子文件组",
                "groupKey": "post",
                "files": [
                ]
            },
            {
                "inputPath": "D:/AWorkspace/Code/Project1/ldzx-CodeGenerator/ldzx-codegenerator-maker/ldzx-codegenerator-maker/.temp/1/meta.json",
                "outputPath": "D:/AWorkspace/Code/Project1/ldzx-CodeGenerator/ldzx-codegenerator-maker/ldzx-codegenerator-maker/.temp/1/meta.json",
                "type": "file",
                "generateType": "static"
            }
        ]
    },
    "modelConfig": {
        "models": [
            {
                "type": "MysqlConfig",
                "description": "用于生成MySQL数据库配置",
                "groupKey": "mysqlConfig",
                "groupName": "MySQL数据库配置",
                "models": [
                    {
                        "fieldName": "password",
                        "type": "String",
                        "description": "密码",
                        "defaultValue": "123456"
                    },
                    {
                        "fieldName": "url",
                        "type": "String",
                        "description": "地址",
                        "defaultValue": "jdbc:mysql://localhost:3306/my_db"
                    },
                    {
                        "fieldName": "username",
                        "type": "String",
                        "description": "用户名",
                        "defaultValue": "root"
                    }
                ]
            },
            {
                "type": "DocsConfig",
                "description": "用于生成接口文档配置",
                "groupKey": "docsConfig",
                "groupName": "接口文档配置",
                "condition": "needDocs",
                "models": [
                    {
                        "fieldName": "description",
                        "type": "String",
                        "description": "接口文档描述",
                        "defaultValue": "springboot-init"
                    },
                    {
                        "fieldName": "title",
                        "type": "String",
                        "description": "接口文档标题",
                        "defaultValue": "接口文档"
                    },
                    {
                        "fieldName": "version",
                        "type": "String",
                        "description": "接口文档版本",
                        "defaultValue": "1.0"
                    }
                ]
            },
            {
                "fieldName": "needDocs",
                "type": "boolean",
                "description": "是否开启接口文档功能",
                "defaultValue": true
            },
            {
                "fieldName": "needPost",
                "type": "boolean",
                "description": "是否开启帖子功能",
                "defaultValue": true
            },
            {
                "fieldName": "needCors",
                "type": "boolean",
                "description": "是否开启跨域功能",
                "defaultValue": true
            },
            {
                "fieldName": "basePackage",
                "type": "String",
                "description": "基础包名",
                "defaultValue": "${basePackage}"
            },
            {
                "fieldName": "needEs",
                "type": "boolean",
                "description": "是否开启ES功能",
                "defaultValue": true
            },
            {
                "fieldName": "needRedis",
                "type": "boolean",
                "description": "是否开启Redis功能",
                "defaultValue": true
            }
        ]
    }
}