package cn.iocoder.yudao.module.validation.controller.admin.reportdefinition.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 报表表样 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportDefinitionRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "18396")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "报表编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21812")
    private Long reportId;

    @Schema(description = "报表", example = "21812")
    @ExcelProperty("报表")
    private String reportName;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("名字")
    private String name;

    @Schema(description = "列序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("列序号")
    private Integer columnIndex;

    @Schema(description = "状态", example = "1")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}