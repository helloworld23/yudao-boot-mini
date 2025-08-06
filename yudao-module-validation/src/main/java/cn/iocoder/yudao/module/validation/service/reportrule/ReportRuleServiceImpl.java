package cn.iocoder.yudao.module.validation.service.reportrule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.validation.controller.admin.reportrule.vo.ReportRulePageReqVO;
import cn.iocoder.yudao.module.validation.controller.admin.reportrule.vo.ReportRuleSaveReqVO;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdata.ReportDataDO;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdefinition.ReportDefinitionDO;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportrule.ReportRuleDO;
import cn.iocoder.yudao.module.validation.dal.mysql.reportrule.ReportRuleMapper;
import cn.iocoder.yudao.module.validation.engine.AviatorUtil;
import cn.iocoder.yudao.module.validation.service.report.ReportService;
import cn.iocoder.yudao.module.validation.service.reportdata.ReportDataService;
import cn.iocoder.yudao.module.validation.service.reportdefinition.ReportDefinitionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import cn.hutool.core.collection.CollUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.validation.enums.ErrorCodeConstants.REPORT_RULE_NOT_EXISTS;

/**
 * 校验规则 Service 实现类
 *
 * @author 钟子明
 */
@Service
@Validated
public class ReportRuleServiceImpl implements ReportRuleService {

    @Resource
    private ReportDataService reportDataService;

    @Resource
    private ReportService reportService;

    @Resource
    private ReportDefinitionService reportDefinitionService;

    @Resource
    private ReportRuleMapper reportRuleMapper;


    @Override
    public Long createReportRule(ReportRuleSaveReqVO createReqVO) {
        // 插入
        ReportRuleDO reportRule = BeanUtils.toBean(createReqVO, ReportRuleDO.class);
        reportRuleMapper.insert(reportRule);
        // 返回
        return reportRule.getId();
    }

    @Override
    public void updateReportRule(ReportRuleSaveReqVO updateReqVO) {
        // 校验存在
        validateReportRuleExists(updateReqVO.getId());
        // 更新
        ReportRuleDO updateObj = BeanUtils.toBean(updateReqVO, ReportRuleDO.class);
        reportRuleMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportRule(Long id) {
        // 校验存在
        validateReportRuleExists(id);
        // 删除
        reportRuleMapper.deleteById(id);
    }

    @Override
        public void deleteReportRuleListByIds(List<Long> ids) {
        // 校验存在
        validateReportRuleExists(ids);
        // 删除
        reportRuleMapper.deleteByIds(ids);
        }

    private void validateReportRuleExists(List<Long> ids) {
        List<ReportRuleDO> list = reportRuleMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(REPORT_RULE_NOT_EXISTS);
        }
    }

    private void validateReportRuleExists(Long id) {
        if (reportRuleMapper.selectById(id) == null) {
            throw exception(REPORT_RULE_NOT_EXISTS);
        }
    }

    @Override
    public ReportRuleDO getReportRule(Long id) {
        return reportRuleMapper.selectById(id);
    }

    @Override
    public PageResult<ReportRuleDO> getReportRulePage(ReportRulePageReqVO pageReqVO) {
        return reportRuleMapper.selectPage(pageReqVO);
    }

    @Override
    public void validateReportRuleListByIds(List<Long> ids) {
        List<ReportRuleDO> list = reportRuleMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list)) {
            throw exception(REPORT_RULE_NOT_EXISTS);
        }

        // 获取数据
        List<Long> reportIds = list.stream()
                .filter(rule -> rule.getInvolvedTables() != null && !rule.getInvolvedTables().isEmpty())
                .flatMap(rule -> {
                    // 去掉左右方括号
                    String cleaned = rule.getInvolvedTables().replaceAll("\\[|\\]", "");
                    return Arrays.stream(cleaned.split(","));
                })
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::valueOf)
                .collect(Collectors.toList());
        Map<Long, List<ReportDataDO>> dataMap = reportDataService.getReportDataByReportIds(reportIds);

        Function<Long, List<ReportDefinitionDO>> reportDefinitionLoader = reportDefinitionService::getReportDefinitionByReportId;
        Function<Long, String> reportNameLoader = reportService::getReportNameById;

        // 数据校验
        for (ReportRuleDO reportRule : list) {
            Map<String, List<Map<String, Object>>> params = AviatorUtil.buildParamByRule(reportRule, dataMap, reportDefinitionLoader, reportNameLoader);
            // 校验
            boolean result = AviatorUtil.validate(reportRule, params);
        }
    }

}