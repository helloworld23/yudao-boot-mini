package cn.iocoder.yudao.module.validation.dal.mysql.reportdata;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdata.ReportDataDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.validation.controller.admin.reportdata.vo.*;

/**
 * 报表数据 Mapper
 *
 * @author 钟子明
 */
@Mapper
public interface ReportDataMapper extends BaseMapperX<ReportDataDO> {

    default PageResult<ReportDataDO> selectPage(ReportDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportDataDO>()
                .eqIfPresent(ReportDataDO::getReportId, reqVO.getReportId())
                .eqIfPresent(ReportDataDO::getColumnId, reqVO.getColumnId())
                .likeIfPresent(ReportDataDO::getValue, reqVO.getValue())
                .betweenIfPresent(ReportDataDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportDataDO::getId));
    }

}