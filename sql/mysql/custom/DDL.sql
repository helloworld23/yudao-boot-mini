CREATE TABLE `validation_report` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                     `category` int DEFAULT NULL COMMENT '分类',
                                     `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编码',
                                     `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名字',
                                     `file` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件',
                                     `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
                                     `status` tinyint DEFAULT NULL COMMENT '状态',
                                     `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报表定义'

CREATE TABLE `validation_report_data` (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                          `report_id` bigint NOT NULL COMMENT '报表',
                                          `column_id` bigint NOT NULL COMMENT '字段',
                                          `row_index` int NOT NULL COMMENT '行序号',
                                          `column_index` int NOT NULL COMMENT '列序号',
                                          `value` text COLLATE utf8mb4_unicode_ci COMMENT '值',
                                          `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                          `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报表数据'

CREATE TABLE `validation_report_definition` (
                                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                `report_id` bigint NOT NULL COMMENT '报表编号',
                                                `column_index` int NOT NULL COMMENT '列序号',
                                                `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名字',
                                                `definition_type` int DEFAULT '0' COMMENT '字段类型',
                                                `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
                                                `status` tinyint DEFAULT NULL COMMENT '状态',
                                                `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
                                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
                                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                                `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报表表样'

CREATE TABLE `validation_report_rule` (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                          `rule_code` varchar(64) NOT NULL COMMENT '校验规则编号',
                                          `report_id` bigint NOT NULL COMMENT '报文名称',
                                          `field_id` bigint NOT NULL COMMENT '字段名称',
                                          `rule_category` int NOT NULL COMMENT '规则大类',
                                          `rule_type` int NOT NULL COMMENT '规则细类',
                                          `validation_flag` int NOT NULL COMMENT '校验标识',
                                          `rule_description` text COMMENT '规则说明',
                                          `involved_tables` varchar(255) DEFAULT NULL COMMENT '涉及的表',
                                          `rule_logic` text COMMENT '规则实现逻辑',
                                          `condition_expression` text COMMENT '限定条件',
                                          `relation_expression` text COMMENT '关联关系',
                                          `description` text COMMENT '备注',
                                          `status` tinyint DEFAULT '0' COMMENT '状态',
                                          `creator` varchar(64) DEFAULT '' COMMENT '创建者',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `updater` varchar(64) DEFAULT '' COMMENT '更新者',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                          `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `rule_code` (`rule_code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='校验规则'

