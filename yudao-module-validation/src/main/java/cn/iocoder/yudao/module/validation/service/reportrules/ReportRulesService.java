package cn.iocoder.yudao.module.validation.service.reportrules;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.validation.controller.admin.reportrules.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportrules.ReportRulesDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 校验规则 Service 接口
 *
 * @author 钟子明
 */
public interface ReportRulesService {

    /**
     * 创建校验规则
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportRules(@Valid ReportRulesSaveReqVO createReqVO);

    /**
     * 更新校验规则
     *
     * @param updateReqVO 更新信息
     */
    void updateReportRules(@Valid ReportRulesSaveReqVO updateReqVO);

    /**
     * 删除校验规则
     *
     * @param id 编号
     */
    void deleteReportRules(Long id);

    /**
    * 批量删除校验规则
    *
    * @param ids 编号
    */
    void deleteReportRulesListByIds(List<Long> ids);

    /**
     * 获得校验规则
     *
     * @param id 编号
     * @return 校验规则
     */
    ReportRulesDO getReportRules(Long id);

    /**
     * 获得校验规则分页
     *
     * @param pageReqVO 分页查询
     * @return 校验规则分页
     */
    PageResult<ReportRulesDO> getReportRulesPage(ReportRulesPageReqVO pageReqVO);

}