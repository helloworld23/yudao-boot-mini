package cn.iocoder.yudao.module.validation.service.reportrule;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.validation.controller.admin.reportrule.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportrule.ReportRuleDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 校验规则 Service 接口
 *
 * @author 钟子明
 */
public interface ReportRuleService {

    /**
     * 创建校验规则
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportRule(@Valid ReportRuleSaveReqVO createReqVO);

    /**
     * 更新校验规则
     *
     * @param updateReqVO 更新信息
     */
    void updateReportRule(@Valid ReportRuleSaveReqVO updateReqVO);

    /**
     * 删除校验规则
     *
     * @param id 编号
     */
    void deleteReportRule(Long id);

    /**
    * 批量删除校验规则
    *
    * @param ids 编号
    */
    void deleteReportRuleListByIds(List<Long> ids);

    /**
     * 获得校验规则
     *
     * @param id 编号
     * @return 校验规则
     */
    ReportRuleDO getReportRule(Long id);

    /**
     * 获得校验规则分页
     *
     * @param pageReqVO 分页查询
     * @return 校验规则分页
     */
    PageResult<ReportRuleDO> getReportRulePage(ReportRulePageReqVO pageReqVO);

    /**
     * 根据规则进行校验
     * @param ids
     */
    void validateReportRuleListByIds(List<Long> ids);
}