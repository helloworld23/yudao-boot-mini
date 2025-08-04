package cn.iocoder.yudao.module.validation.dal.mysql.reportrule;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportrule.ReportRuleDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.validation.controller.admin.reportrule.vo.*;

/**
 * 校验规则 Mapper
 *
 * @author 钟子明
 */
@Mapper
public interface ReportRuleMapper extends BaseMapperX<ReportRuleDO> {

    default PageResult<ReportRuleDO> selectPage(ReportRulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportRuleDO>()
                .eqIfPresent(ReportRuleDO::getRuleCode, reqVO.getRuleCode())
                .likeIfPresent(ReportRuleDO::getReportName, reqVO.getReportName())
                .likeIfPresent(ReportRuleDO::getFieldName, reqVO.getFieldName())
                .betweenIfPresent(ReportRuleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportRuleDO::getId));
    }

}