package cn.iocoder.yudao.module.validation.controller.admin.reportrules;

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

import cn.iocoder.yudao.module.validation.controller.admin.reportrules.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportrules.ReportRulesDO;
import cn.iocoder.yudao.module.validation.service.reportrules.ReportRulesService;

@Tag(name = "管理后台 - 校验规则")
@RestController
@RequestMapping("/validation/report-rules")
@Validated
public class ReportRulesController {

    @Resource
    private ReportRulesService reportRulesService;

    @PostMapping("/create")
    @Operation(summary = "创建校验规则")
    @PreAuthorize("@ss.hasPermission('validation:report-rules:create')")
    public CommonResult<Long> createReportRules(@Valid @RequestBody ReportRulesSaveReqVO createReqVO) {
        return success(reportRulesService.createReportRules(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新校验规则")
    @PreAuthorize("@ss.hasPermission('validation:report-rules:update')")
    public CommonResult<Boolean> updateReportRules(@Valid @RequestBody ReportRulesSaveReqVO updateReqVO) {
        reportRulesService.updateReportRules(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除校验规则")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('validation:report-rules:delete')")
    public CommonResult<Boolean> deleteReportRules(@RequestParam("id") Long id) {
        reportRulesService.deleteReportRules(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除校验规则")
                @PreAuthorize("@ss.hasPermission('validation:report-rules:delete')")
    public CommonResult<Boolean> deleteReportRulesList(@RequestParam("ids") List<Long> ids) {
        reportRulesService.deleteReportRulesListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得校验规则")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('validation:report-rules:query')")
    public CommonResult<ReportRulesRespVO> getReportRules(@RequestParam("id") Long id) {
        ReportRulesDO reportRules = reportRulesService.getReportRules(id);
        return success(BeanUtils.toBean(reportRules, ReportRulesRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得校验规则分页")
    @PreAuthorize("@ss.hasPermission('validation:report-rules:query')")
    public CommonResult<PageResult<ReportRulesRespVO>> getReportRulesPage(@Valid ReportRulesPageReqVO pageReqVO) {
        PageResult<ReportRulesDO> pageResult = reportRulesService.getReportRulesPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportRulesRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出校验规则 Excel")
    @PreAuthorize("@ss.hasPermission('validation:report-rules:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportRulesExcel(@Valid ReportRulesPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportRulesDO> list = reportRulesService.getReportRulesPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "校验规则.xls", "数据", ReportRulesRespVO.class,
                        BeanUtils.toBean(list, ReportRulesRespVO.class));
    }

}