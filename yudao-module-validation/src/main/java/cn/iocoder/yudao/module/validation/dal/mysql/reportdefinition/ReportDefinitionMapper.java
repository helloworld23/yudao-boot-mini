package cn.iocoder.yudao.module.validation.dal.mysql.reportdefinition;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdefinition.ReportDefinitionDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.validation.controller.admin.reportdefinition.vo.*;

/**
 * 报表表样 Mapper
 *
 * @author 钟子明
 */
@Mapper
public interface ReportDefinitionMapper extends BaseMapperX<ReportDefinitionDO> {

    default PageResult<ReportDefinitionDO> selectPage(ReportDefinitionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportDefinitionDO>()
                .eqIfPresent(ReportDefinitionDO::getReportId, reqVO.getReportId())
                .likeIfPresent(ReportDefinitionDO::getName, reqVO.getName())
                .eqIfPresent(ReportDefinitionDO::getColumnIndex, reqVO.getColumnIndex())
                .eqIfPresent(ReportDefinitionDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ReportDefinitionDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(ReportDefinitionDO::getColumnIndex));
    }

    default List<ReportDefinitionDO> selectByReportId(Long id){
        return selectList(new LambdaQueryWrapperX<ReportDefinitionDO>()
                .eq(ReportDefinitionDO::getReportId, id));
    }
}