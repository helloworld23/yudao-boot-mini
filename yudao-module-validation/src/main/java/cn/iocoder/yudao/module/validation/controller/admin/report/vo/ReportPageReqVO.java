package cn.iocoder.yudao.module.validation.controller.admin.report.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报表定义分页 Request VO")
@Data
public class ReportPageReqVO extends PageParam {

    @Schema(description = "分类", example = "2")
    private Integer category;

    @Schema(description = "名字", example = "芋艿")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "文件")
    private String file;

    @Schema(description = "状态", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}