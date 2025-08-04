package cn.iocoder.yudao.module.validation.controller.admin.report.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 报表定义 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15916")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "分类", example = "2")
    @ExcelProperty(value = "分类", converter = DictConvert.class)
    @DictFormat("validation_category") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer category;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("名字")
    private String name;

    @Schema(description = "编码")
    @ExcelProperty("编码")
    private String code;

    @Schema(description = "文件")
    @ExcelProperty("文件")
    private String file;

    @Schema(description = "描述", example = "随便")
    @ExcelProperty("描述")
    private String description;

    @Schema(description = "状态", example = "2")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}