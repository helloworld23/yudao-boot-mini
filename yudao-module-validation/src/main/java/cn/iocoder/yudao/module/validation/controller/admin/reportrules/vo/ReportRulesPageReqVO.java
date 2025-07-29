package cn.iocoder.yudao.module.validation.controller.admin.reportrules.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 校验规则分页 Request VO")
@Data
public class ReportRulesPageReqVO extends PageParam {

    @Schema(description = "规则名称", example = "张三")
    private String name;

    @Schema(description = "报表编号", example = "31147")
    private Long reportId;

    @Schema(description = "字段编号", example = "15251")
    private Long reportDefinitionId;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}