package cn.iocoder.yudao.module.validation.controller.admin.report.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 报表定义新增/修改 Request VO")
@Data
public class ReportSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15916")
    private Long id;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "名字不能为空")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "文件")
    private String file;

    @Schema(description = "描述", example = "随便")
    private String description;

    @Schema(description = "状态", example = "2")
    private Integer status;

}