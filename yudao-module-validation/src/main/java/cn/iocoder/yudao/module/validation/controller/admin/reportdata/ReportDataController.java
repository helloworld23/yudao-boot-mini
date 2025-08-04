package cn.iocoder.yudao.module.validation.controller.admin.reportdata;

import cn.iocoder.yudao.module.validation.convert.ReportConvert;
import cn.iocoder.yudao.module.validation.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdefinition.ReportDefinitionDO;
import cn.iocoder.yudao.module.validation.service.report.ReportService;
import cn.iocoder.yudao.module.validation.service.reportdefinition.ReportDefinitionService;
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
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

import cn.iocoder.yudao.module.validation.controller.admin.reportdata.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdata.ReportDataDO;
import cn.iocoder.yudao.module.validation.service.reportdata.ReportDataService;

@Tag(name = "管理后台 - 报表数据")
@RestController
@RequestMapping("/validation/report-data")
@Validated
public class ReportDataController {

    @Resource
    private ReportService reportService;

    @Resource
    private ReportDefinitionService reportDefinitionService;

    @Resource
    private ReportDataService reportDataService;

    @PostMapping("/create")
    @Operation(summary = "创建报表数据")
    @PreAuthorize("@ss.hasPermission('validation:report-data:create')")
    public CommonResult<Long> createReportData(@Valid @RequestBody ReportDataSaveReqVO createReqVO) {
        return success(reportDataService.createReportData(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报表数据")
    @PreAuthorize("@ss.hasPermission('validation:report-data:update')")
    public CommonResult<Boolean> updateReportData(@Valid @RequestBody ReportDataSaveReqVO updateReqVO) {
        reportDataService.updateReportData(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报表数据")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('validation:report-data:delete')")
    public CommonResult<Boolean> deleteReportData(@RequestParam("id") Long id) {
        reportDataService.deleteReportData(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除报表数据")
                @PreAuthorize("@ss.hasPermission('validation:report-data:delete')")
    public CommonResult<Boolean> deleteReportDataList(@RequestParam("ids") List<Long> ids) {
        reportDataService.deleteReportDataListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报表数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('validation:report-data:query')")
    public CommonResult<ReportDataRespVO> getReportData(@RequestParam("id") Long id) {
        ReportDataDO reportData = reportDataService.getReportData(id);
        return success(BeanUtils.toBean(reportData, ReportDataRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报表数据分页")
    @PreAuthorize("@ss.hasPermission('validation:report-data:query')")
    public CommonResult<PageResult<ReportDataRespVO>> getReportDataPage(@Valid ReportDataPageReqVO pageReqVO) {
        PageResult<ReportDataDO> pageResult = reportDataService.getReportDataPage(pageReqVO);
        // 拼接数据
        Map<Long, ReportDO> reportMap = reportService.getReportMap(
                convertList(pageResult.getList(), ReportDataDO::getReportId));

        Map<Long, ReportDefinitionDO> reportDefinitionMap = reportDefinitionService.getReportDefinitionMap(
                convertList(pageResult.getList(), ReportDataDO::getReportId));

        // 3. 转换数据并补充 columnName
        List<ReportDataRespVO> respList = pageResult.getList().stream().map(data -> {
            ReportDataRespVO vo = ReportConvert.INSTANCE.convertData(data, reportMap.get(data.getReportId()));
            ReportDefinitionDO def = reportDefinitionMap.get(data.getColumnId());
            if (def != null) {
                vo.setColumnName(def.getName());
            }
            return vo;
        }).toList();

        // 4. 返回结果
        return success(new PageResult<>(respList, pageResult.getTotal()));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报表数据 Excel")
    @PreAuthorize("@ss.hasPermission('validation:report-data:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportDataExcel(@Valid ReportDataPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportDataDO> list = reportDataService.getReportDataPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "报表数据.xls", "数据", ReportDataRespVO.class,
                        BeanUtils.toBean(list, ReportDataRespVO.class));
    }

}