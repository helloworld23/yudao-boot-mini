-- 菜单 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status, component_name
)
VALUES (
    '报表表样管理', '', 2, 0, 5013,
    'report-definition', '', 'validation/reportdefinition/index', 0, 'ReportDefinition'
);

-- 按钮父菜单ID
-- 暂时只支持 MySQL。如果你是 Oracle、PostgreSQL、SQLServer 的话，需要手动修改 @parentId 的部分的代码
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '报表表样查询', 'validation:report-definition:query', 3, 1, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '报表表样创建', 'validation:report-definition:create', 3, 2, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '报表表样更新', 'validation:report-definition:update', 3, 3, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '报表表样删除', 'validation:report-definition:delete', 3, 4, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '报表表样导出', 'validation:report-definition:export', 3, 5, @parentId,
    '', '', '', 0
);

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status)
VALUES ('查看规则', 'validation:report-definition:view', 3, 6, @parentId,
        '', '', '', 0);
