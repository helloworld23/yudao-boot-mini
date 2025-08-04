package cn.iocoder.yudao.module.validation.controller.admin.reportdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 报表数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportDataRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "9719")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "报表", requiredMode = Schema.RequiredMode.REQUIRED, example = "821")
    private Long reportId;

    @ExcelProperty("报表")
    private String reportName;

    @Schema(description = "字段", requiredMode = Schema.RequiredMode.REQUIRED, example = "7872")
    private Long columnId;

    @ExcelProperty("字段")
    private String columnName;

    @Schema(description = "行序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("行序号")
    private Integer rowIndex;

    @Schema(description = "列序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("列序号")
    private Integer columnIndex;

    @Schema(description = "值")
    @ExcelProperty("值")
    private String value;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
}