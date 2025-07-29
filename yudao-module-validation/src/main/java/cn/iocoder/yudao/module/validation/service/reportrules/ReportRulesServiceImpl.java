package cn.iocoder.yudao.module.validation.service.reportrules;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.validation.controller.admin.reportrules.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportrules.ReportRulesDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.validation.dal.mysql.reportrules.ReportRulesMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.validation.enums.ErrorCodeConstants.*;

/**
 * 校验规则 Service 实现类
 *
 * @author 钟子明
 */
@Service
@Validated
public class ReportRulesServiceImpl implements ReportRulesService {

    @Resource
    private ReportRulesMapper reportRulesMapper;

    @Override
    public Long createReportRules(ReportRulesSaveReqVO createReqVO) {
        // 插入
        ReportRulesDO reportRules = BeanUtils.toBean(createReqVO, ReportRulesDO.class);
        reportRulesMapper.insert(reportRules);
        // 返回
        return reportRules.getId();
    }

    @Override
    public void updateReportRules(ReportRulesSaveReqVO updateReqVO) {
        // 校验存在
        validateReportRulesExists(updateReqVO.getId());
        // 更新
        ReportRulesDO updateObj = BeanUtils.toBean(updateReqVO, ReportRulesDO.class);
        reportRulesMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportRules(Long id) {
        // 校验存在
        validateReportRulesExists(id);
        // 删除
        reportRulesMapper.deleteById(id);
    }

    @Override
        public void deleteReportRulesListByIds(List<Long> ids) {
        // 校验存在
        validateReportRulesExists(ids);
        // 删除
        reportRulesMapper.deleteByIds(ids);
        }

    private void validateReportRulesExists(List<Long> ids) {
        List<ReportRulesDO> list = reportRulesMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(REPORT_RULES_NOT_EXISTS);
        }
    }

    private void validateReportRulesExists(Long id) {
        if (reportRulesMapper.selectById(id) == null) {
            throw exception(REPORT_RULES_NOT_EXISTS);
        }
    }

    @Override
    public ReportRulesDO getReportRules(Long id) {
        return reportRulesMapper.selectById(id);
    }

    @Override
    public PageResult<ReportRulesDO> getReportRulesPage(ReportRulesPageReqVO pageReqVO) {
        return reportRulesMapper.selectPage(pageReqVO);
    }

}