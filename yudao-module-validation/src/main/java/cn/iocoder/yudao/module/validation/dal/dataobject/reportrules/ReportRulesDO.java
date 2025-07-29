package cn.iocoder.yudao.module.validation.dal.dataobject.reportrules;

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
@TableName("validation_report_rules")
@KeySequence("validation_report_rules_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRulesDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 规则名称
     */
    private String name;
    /**
     * 报表编号
     */
    private Long reportId;
    /**
     * 字段编号
     */
    private Long reportDefinitionId;
    /**
     * 值类型
     *
     * 枚举 {@link TODO validation_value_type 对应的类}
     */
    private Integer valueType;
    /**
     * 规则类型
     *
     * 枚举 {@link TODO validation_compare_type 对应的类}
     */
    private Integer compareType;
    /**
     * 维度字段
     */
    private Integer dimension;
    /**
     * 目标报表编号
     */
    private Long targetReportId;
    /**
     * 描述
     */
    private String description;
    /**
     * 目标字段编号
     */
    private Long targetReportDefinitionId;
    /**
     * 状态
     */
    private Integer status;


}