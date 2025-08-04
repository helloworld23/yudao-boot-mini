package cn.iocoder.yudao.module.validation.controller.admin.reportdata.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报表数据分页 Request VO")
@Data
public class ReportDataPageReqVO extends PageParam {

    @Schema(description = "报表", example = "821")
    private Long reportId;

    @Schema(description = "字段", example = "7872")
    private Long columnId;

    @Schema(description = "值")
    private String value;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}