/*此为项目化的脚本*/
create table `ruoyi-vue-pro`.validation_report
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(64)                           null comment '编码',
    name        varchar(255)                          not null comment '名字',
    file        varchar(255)                          null comment '文件',
    description varchar(512)                          null comment '描述',
    status      tinyint                               null comment '状态',
    creator     varchar(64) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(64) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit         default b'0'              not null comment '是否删除',
    tenant_id   bigint      default 0                 not null comment '租户编号'
)
    comment '报表定义' collate = utf8mb4_unicode_ci;

create table `ruoyi-vue-pro`.validation_report_definition
(
    id           bigint auto_increment comment '主键'
        primary key,
    report_id    bigint                                not null comment '报表编号',
    name         varchar(255)                          not null comment '名字',
    column_index int                                   not null comment '列序号',
    description  varchar(512)                          null comment '描述',
    status       tinyint                               null comment '状态',
    creator      varchar(64) default ''                null comment '创建者',
    create_time  datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      varchar(64) default ''                null comment '更新者',
    update_time  datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      bit         default b'0'              not null comment '是否删除',
    tenant_id    bigint      default 0                 not null comment '租户编号'
)
    comment '报表表样' collate = utf8mb4_unicode_ci;

create table `ruoyi-vue-pro`.validation_report_rules
(
    id                          bigint auto_increment comment '主键'
        primary key,
    name                        varchar(255)                          not null comment '规则名称',
    report_id                   bigint                                not null comment '报表编号',
    report_definition_id        bigint                                not null comment '字段编号',
    value_type                  int                                   not null comment '值类型',
    dimension                   int                                   null comment '维度字段',
    compare_type                int                                   not null comment '规则类型',
    target_report_id            bigint                                null comment '目标报表编号',
    target_report_definition_id bigint                                null comment '目标字段编号',
    description                 varchar(512)                          null comment '描述',
    status                      tinyint                               null comment '状态',
    creator                     varchar(64) default ''                null comment '创建者',
    create_time                 datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater                     varchar(64) default ''                null comment '更新者',
    update_time                 datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted                     bit         default b'0'              not null comment '是否删除',
    tenant_id                   bigint      default 0                 not null comment '租户编号'
)
    comment '校验规则' collate = utf8mb4_unicode_ci;

