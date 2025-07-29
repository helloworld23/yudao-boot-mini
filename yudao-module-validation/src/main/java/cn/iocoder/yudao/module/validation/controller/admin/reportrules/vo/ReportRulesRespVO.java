package cn.iocoder.yudao.module.validation.controller.admin.reportrules.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 校验规则 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportRulesRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "20289")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "规则名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("规则名称")
    private String name;

    @Schema(description = "报表编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31147")
    @ExcelProperty("报表编号")
    private Long reportId;

    @Schema(description = "字段编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15251")
    @ExcelProperty("字段编号")
    private Long reportDefinitionId;

    @Schema(description = "值类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "值类型", converter = DictConvert.class)
    @DictFormat("validation_value_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer valueType;

    @Schema(description = "规则类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "规则类型", converter = DictConvert.class)
    @DictFormat("validation_compare_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer compareType;

    @Schema(description = "维度字段")
    @ExcelProperty("维度字段")
    private Integer dimension;

    @Schema(description = "目标报表编号", example = "8868")
    @ExcelProperty("目标报表编号")
    private Long targetReportId;

    @Schema(description = "描述", example = "随便")
    @ExcelProperty("描述")
    private String description;

    @Schema(description = "目标字段编号", example = "29")
    @ExcelProperty("目标字段编号")
    private Long targetReportDefinitionId;

    @Schema(description = "状态", example = "1")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}