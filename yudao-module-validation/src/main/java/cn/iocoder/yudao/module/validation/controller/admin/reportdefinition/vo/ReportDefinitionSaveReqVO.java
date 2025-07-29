package cn.iocoder.yudao.module.validation.controller.admin.reportdefinition.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 报表表样新增/修改 Request VO")
@Data
public class ReportDefinitionSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "18396")
    private Long id;

    @Schema(description = "报表编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21812")
    @NotNull(message = "报表编号不能为空")
    private Long reportId;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "名字不能为空")
    private String name;

    @Schema(description = "描述", example = "随便")
    private String description;

    @Schema(description = "列序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "列序号不能为空")
    private Integer columnIndex;

    @Schema(description = "状态", example = "1")
    private Integer status;

}