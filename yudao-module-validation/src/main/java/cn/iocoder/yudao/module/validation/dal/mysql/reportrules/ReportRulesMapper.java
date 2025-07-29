package cn.iocoder.yudao.module.validation.dal.mysql.reportrules;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportrules.ReportRulesDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.validation.controller.admin.reportrules.vo.*;

/**
 * 校验规则 Mapper
 *
 * @author 钟子明
 */
@Mapper
public interface ReportRulesMapper extends BaseMapperX<ReportRulesDO> {

    default PageResult<ReportRulesDO> selectPage(ReportRulesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportRulesDO>()
                .likeIfPresent(ReportRulesDO::getName, reqVO.getName())
                .eqIfPresent(ReportRulesDO::getReportId, reqVO.getReportId())
                .eqIfPresent(ReportRulesDO::getReportDefinitionId, reqVO.getReportDefinitionId())
                .eqIfPresent(ReportRulesDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ReportRulesDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportRulesDO::getId));
    }

}