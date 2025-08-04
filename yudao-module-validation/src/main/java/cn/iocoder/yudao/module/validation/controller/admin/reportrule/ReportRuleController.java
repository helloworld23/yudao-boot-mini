package cn.iocoder.yudao.module.validation.controller.admin.reportrule;

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

import cn.iocoder.yudao.module.validation.controller.admin.reportrule.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportrule.ReportRuleDO;
import cn.iocoder.yudao.module.validation.service.reportrule.ReportRuleService;

@Tag(name = "管理后台 - 校验规则")
@RestController
@RequestMapping("/validation/report-rule")
@Validated
public class ReportRuleController {

    @Resource
    private ReportRuleService reportRuleService;

    @PostMapping("/create")
    @Operation(summary = "创建校验规则")
    @PreAuthorize("@ss.hasPermission('validation:report-rule:create')")
    public CommonResult<Long> createReportRule(@Valid @RequestBody ReportRuleSaveReqVO createReqVO) {
        return success(reportRuleService.createReportRule(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新校验规则")
    @PreAuthorize("@ss.hasPermission('validation:report-rule:update')")
    public CommonResult<Boolean> updateReportRule(@Valid @RequestBody ReportRuleSaveReqVO updateReqVO) {
        reportRuleService.updateReportRule(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除校验规则")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('validation:report-rule:delete')")
    public CommonResult<Boolean> deleteReportRule(@RequestParam("id") Long id) {
        reportRuleService.deleteReportRule(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除校验规则")
                @PreAuthorize("@ss.hasPermission('validation:report-rule:delete')")
    public CommonResult<Boolean> deleteReportRuleList(@RequestParam("ids") List<Long> ids) {
        reportRuleService.deleteReportRuleListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得校验规则")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('validation:report-rule:query')")
    public CommonResult<ReportRuleRespVO> getReportRule(@RequestParam("id") Long id) {
        ReportRuleDO reportRule = reportRuleService.getReportRule(id);
        return success(BeanUtils.toBean(reportRule, ReportRuleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得校验规则分页")
    @PreAuthorize("@ss.hasPermission('validation:report-rule:query')")
    public CommonResult<PageResult<ReportRuleRespVO>> getReportRulePage(@Valid ReportRulePageReqVO pageReqVO) {
        PageResult<ReportRuleDO> pageResult = reportRuleService.getReportRulePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportRuleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出校验规则 Excel")
    @PreAuthorize("@ss.hasPermission('validation:report-rule:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportRuleExcel(@Valid ReportRulePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportRuleDO> list = reportRuleService.getReportRulePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "校验规则.xls", "数据", ReportRuleRespVO.class,
                        BeanUtils.toBean(list, ReportRuleRespVO.class));
    }

}