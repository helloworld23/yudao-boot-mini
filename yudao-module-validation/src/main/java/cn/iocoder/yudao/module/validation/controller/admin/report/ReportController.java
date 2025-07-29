package cn.iocoder.yudao.module.validation.controller.admin.report;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.validation.controller.admin.report.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.module.validation.service.report.ReportService;

@Tag(name = "管理后台 - 报表定义")
@RestController
@RequestMapping("/validation/report")
@Validated
public class ReportController {

    @Resource
    private ReportService reportService;

    @PostMapping("/create")
    @Operation(summary = "创建报表定义")
    @PreAuthorize("@ss.hasPermission('validation:report:create')")
    public CommonResult<Long> createReport(@Valid @RequestBody ReportSaveReqVO createReqVO) {
        return success(reportService.createReport(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报表定义")
    @PreAuthorize("@ss.hasPermission('validation:report:update')")
    public CommonResult<Boolean> updateReport(@Valid @RequestBody ReportSaveReqVO updateReqVO) {
        reportService.updateReport(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报表定义")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('validation:report:delete')")
    public CommonResult<Boolean> deleteReport(@RequestParam("id") Long id) {
        reportService.deleteReport(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除报表定义")
                @PreAuthorize("@ss.hasPermission('validation:report:delete')")
    public CommonResult<Boolean> deleteReportList(@RequestParam("ids") List<Long> ids) {
        reportService.deleteReportListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报表定义")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('validation:report:query')")
    public CommonResult<ReportRespVO> getReport(@RequestParam("id") Long id) {
        ReportDO report = reportService.getReport(id);
        return success(BeanUtils.toBean(report, ReportRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报表定义分页")
    @PreAuthorize("@ss.hasPermission('validation:report:query')")
    public CommonResult<PageResult<ReportRespVO>> getReportPage(@Valid ReportPageReqVO pageReqVO) {
        PageResult<ReportDO> pageResult = reportService.getReportPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报表定义 Excel")
    @PreAuthorize("@ss.hasPermission('validation:report:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportExcel(@Valid ReportPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportDO> list = reportService.getReportPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "报表定义.xls", "数据", ReportRespVO.class,
                        BeanUtils.toBean(list, ReportRespVO.class));
    }

}