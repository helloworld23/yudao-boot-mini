package cn.iocoder.yudao.module.validation.controller.admin.reportdefinition;

import cn.iocoder.yudao.module.validation.convert.ReportConvert;
import cn.iocoder.yudao.module.validation.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.module.validation.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

import cn.iocoder.yudao.module.validation.controller.admin.reportdefinition.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdefinition.ReportDefinitionDO;
import cn.iocoder.yudao.module.validation.service.reportdefinition.ReportDefinitionService;

/**
 * @author zhongziming
 */
@Tag(name = "管理后台 - 报表表样")
@RestController
@RequestMapping("/validation/report-definition")
@Validated
public class ReportDefinitionController {

    @Resource
    private ReportDefinitionService reportDefinitionService;
    @Autowired
    private ReportService reportService;

    @PostMapping("/create")
    @Operation(summary = "创建报表表样")
    @PreAuthorize("@ss.hasPermission('validation:report-definition:create')")
    public CommonResult<Long> createReportDefinition(@Valid @RequestBody ReportDefinitionSaveReqVO createReqVO) {
        return success(reportDefinitionService.createReportDefinition(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报表表样")
    @PreAuthorize("@ss.hasPermission('validation:report-definition:update')")
    public CommonResult<Boolean> updateReportDefinition(@Valid @RequestBody ReportDefinitionSaveReqVO updateReqVO) {
        reportDefinitionService.updateReportDefinition(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报表表样")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('validation:report-definition:delete')")
    public CommonResult<Boolean> deleteReportDefinition(@RequestParam("id") Long id) {
        reportDefinitionService.deleteReportDefinition(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除报表表样")
                @PreAuthorize("@ss.hasPermission('validation:report-definition:delete')")
    public CommonResult<Boolean> deleteReportDefinitionList(@RequestParam("ids") List<Long> ids) {
        reportDefinitionService.deleteReportDefinitionListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报表表样")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('validation:report-definition:query')")
    public CommonResult<ReportDefinitionRespVO> getReportDefinition(@RequestParam("id") Long id) {
        ReportDefinitionDO reportDefinition = reportDefinitionService.getReportDefinition(id);
        return success(BeanUtils.toBean(reportDefinition, ReportDefinitionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报表表样分页")
    @PreAuthorize("@ss.hasPermission('validation:report-definition:query')")
    public CommonResult<PageResult<ReportDefinitionRespVO>> getReportDefinitionPage(@Valid ReportDefinitionPageReqVO pageReqVO) {
        PageResult<ReportDefinitionDO> pageResult = reportDefinitionService.getReportDefinitionPage(pageReqVO);
        // 拼接数据
        Map<Long, ReportDO> reportMap = reportService.getReportMap(
                convertList(pageResult.getList(), ReportDefinitionDO::getReportId));
        return success(new PageResult<>(ReportConvert.INSTANCE.convertList(pageResult.getList(), reportMap),
                pageResult.getTotal()));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报表表样 Excel")
    @PreAuthorize("@ss.hasPermission('validation:report-definition:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportDefinitionExcel(@Valid ReportDefinitionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportDefinitionDO> list = reportDefinitionService.getReportDefinitionPage(pageReqVO).getList();
        // 拼接数据
        Map<Long, ReportDO> reportMap = reportService.getReportMap(
                convertList(list, ReportDefinitionDO::getReportId));
        // 导出 Excel
        ExcelUtils.write(response, "报表表样.xls", "数据", ReportDefinitionRespVO.class,
                ReportConvert.INSTANCE.convertList(list, reportMap));
    }

}