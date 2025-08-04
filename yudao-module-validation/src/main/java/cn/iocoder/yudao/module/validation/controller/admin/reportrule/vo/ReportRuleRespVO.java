package cn.iocoder.yudao.module.validation.controller.admin.reportrule.vo;

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
public class ReportRuleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "507")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "校验规则编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("校验规则编号")
    private String ruleCode;

    @Schema(description = "报文名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("报文名称")
    private String reportName;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("字段名称")
    private String fieldName;

    @Schema(description = "规则大类", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("规则大类")
    private Integer ruleCategory;

    @Schema(description = "规则细类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("规则细类")
    private Integer ruleType;

    @Schema(description = "校验标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("校验标识")
    private Integer validationFlag;

    @Schema(description = "规则说明", example = "你说的对")
    @ExcelProperty("规则说明")
    private String ruleDescription;

    @Schema(description = "涉及的表")
    @ExcelProperty("涉及的表")
    private String involvedTables;

    @Schema(description = "规则实现逻辑")
    @ExcelProperty("规则实现逻辑")
    private String ruleLogic;

    @Schema(description = "限定条件")
    @ExcelProperty("限定条件")
    private String conditionExpression;

    @Schema(description = "关联关系")
    @ExcelProperty("关联关系")
    private String relationExpression;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String description;

    @Schema(description = "状态", example = "2")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}