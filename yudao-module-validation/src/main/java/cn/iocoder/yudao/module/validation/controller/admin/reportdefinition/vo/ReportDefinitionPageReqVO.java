package cn.iocoder.yudao.module.validation.controller.admin.reportdefinition.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报表表样分页 Request VO")
@Data
public class ReportDefinitionPageReqVO extends PageParam {

    @Schema(description = "报表编号", example = "21812")
    private Long reportId;

    @Schema(description = "名字", example = "赵六")
    private String name;

    @Schema(description = "列序号")
    private Integer columnIndex;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}