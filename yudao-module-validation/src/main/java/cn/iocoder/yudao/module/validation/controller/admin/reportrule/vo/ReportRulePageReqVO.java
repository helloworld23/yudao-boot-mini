package cn.iocoder.yudao.module.validation.controller.admin.reportrule.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 校验规则分页 Request VO")
@Data
public class ReportRulePageReqVO extends PageParam {

    @Schema(description = "校验规则编号")
    private String ruleCode;

    @Schema(description = "报文名称", example = "芋艿")
    private Long reportId;

    @Schema(description = "字段名称", example = "李四")
    private Long fieldId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}