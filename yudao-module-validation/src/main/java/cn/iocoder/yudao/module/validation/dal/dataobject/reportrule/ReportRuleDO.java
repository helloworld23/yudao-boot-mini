package cn.iocoder.yudao.module.validation.dal.dataobject.reportrule;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 校验规则 DO
 *
 * @author 钟子明
 */
@TableName("validation_report_rule")
@KeySequence("validation_report_rule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRuleDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 校验规则编号
     */
    private String ruleCode;
    /**
     * 报文名称
     */
    private Long reportId;
    /**
     * 字段名称
     */
    private Long fieldId;
    /**
     * 规则大类
     */
    private Integer ruleCategory;
    /**
     * 规则细类
     */
    private Integer ruleType;
    /**
     * 校验标识
     */
    private Integer validationFlag;
    /**
     * 规则说明
     */
    private String ruleDescription;
    /**
     * 涉及的表
     */
    private String involvedTables;
    /**
     * 规则实现逻辑
     */
    private String ruleLogic;
    /**
     * 限定条件
     */
    private String conditionExpression;
    /**
     * 关联关系
     */
    private String relationExpression;
    /**
     * 备注
     */
    private String description;
    /**
     * 状态
     *
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Integer status;


}