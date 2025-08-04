package cn.iocoder.yudao.module.validation.dal.dataobject.reportdata;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 报表数据 DO
 *
 * @author 钟子明
 */
@TableName("validation_report_data")
@KeySequence("validation_report_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDataDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 报表
     */
    private Long reportId;
    /**
     * 字段
     */
    private Long columnId;
    /**
     * 行序号
     */
    private Integer rowIndex;
    /**
     * 列序号
     */
    private Integer columnIndex;
    /**
     * 值
     */
    private String value;


}