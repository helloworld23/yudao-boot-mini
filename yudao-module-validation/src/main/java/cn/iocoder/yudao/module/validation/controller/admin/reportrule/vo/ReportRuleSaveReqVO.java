package cn.iocoder.yudao.module.validation.controller.admin.reportrule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 校验规则新增/修改 Request VO")
@Data
public class ReportRuleSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "507")
    private Long id;

    @Schema(description = "校验规则编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "校验规则编号不能为空")
    private String ruleCode;

    @Schema(description = "报文名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "报文名称不能为空")
    private String reportId;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "字段名称不能为空")
    private String fieldId;

    @Schema(description = "规则大类", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "规则大类不能为空")
    private Integer ruleCategory;

    @Schema(description = "规则细类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "规则细类不能为空")
    private Integer ruleType;

    @Schema(description = "校验标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "校验标识不能为空")
    private Integer validationFlag;

    @Schema(description = "规则说明", example = "你说的对")
    private String ruleDescription;

    @Schema(description = "涉及的表")
    private List<Long> involvedTables;

    @Schema(description = "规则实现逻辑")
    private String ruleLogic;

    @Schema(description = "限定条件")
    private String conditionExpression;

    @Schema(description = "关联关系")
    private String relationExpression;

    @Schema(description = "备注", example = "你说的对")
    private String description;

    @Schema(description = "状态", example = "2")
    private Integer status;

}