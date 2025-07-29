package cn.iocoder.yudao.module.validation.dal.mysql.report;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.validation.dal.dataobject.report.ReportDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.validation.controller.admin.report.vo.*;

/**
 * 报表定义 Mapper
 *
 * @author 钟子明
 */
@Mapper
public interface ReportMapper extends BaseMapperX<ReportDO> {

    default PageResult<ReportDO> selectPage(ReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportDO>()
                .likeIfPresent(ReportDO::getName, reqVO.getName())
                .eqIfPresent(ReportDO::getCode, reqVO.getCode())
                .eqIfPresent(ReportDO::getFile, reqVO.getFile())
                .eqIfPresent(ReportDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ReportDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportDO::getId));
    }

}