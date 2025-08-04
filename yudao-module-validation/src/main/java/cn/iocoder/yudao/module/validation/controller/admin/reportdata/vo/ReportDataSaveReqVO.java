package cn.iocoder.yudao.module.validation.controller.admin.reportdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 报表数据新增/修改 Request VO")
@Data
public class ReportDataSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "9719")
    private Long id;

    @Schema(description = "报表", requiredMode = Schema.RequiredMode.REQUIRED, example = "821")
    @NotNull(message = "报表不能为空")
    private Long reportId;

    @Schema(description = "字段", requiredMode = Schema.RequiredMode.REQUIRED, example = "7872")
    @NotNull(message = "字段不能为空")
    private Long columnId;

    @Schema(description = "行序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "行序号不能为空")
    private Integer rowIndex;

    @Schema(description = "列序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "列序号不能为空")
    private Integer columnIndex;

    @Schema(description = "值")
    private String value;

}