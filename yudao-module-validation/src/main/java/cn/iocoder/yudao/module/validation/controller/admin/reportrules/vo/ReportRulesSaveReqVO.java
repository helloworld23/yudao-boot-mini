package cn.iocoder.yudao.module.validation.controller.admin.reportrules.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 校验规则新增/修改 Request VO")
@Data
public class ReportRulesSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "20289")
    private Long id;

    @Schema(description = "规则名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "规则名称不能为空")
    private String name;

    @Schema(description = "报表编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31147")
    @NotNull(message = "报表编号不能为空")
    private Long reportId;

    @Schema(description = "字段编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15251")
    @NotNull(message = "字段编号不能为空")
    private Long reportDefinitionId;

    @Schema(description = "值类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "值类型不能为空")
    private Integer valueType;

    @Schema(description = "规则类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "规则类型不能为空")
    private Integer compareType;

    @Schema(description = "维度字段")
    private Integer dimension;

    @Schema(description = "目标报表编号", example = "8868")
    private Long targetReportId;

    @Schema(description = "描述", example = "随便")
    private String description;

    @Schema(description = "目标字段编号", example = "29")
    private Long targetReportDefinitionId;

    @Schema(description = "状态", example = "1")
    private Integer status;

}