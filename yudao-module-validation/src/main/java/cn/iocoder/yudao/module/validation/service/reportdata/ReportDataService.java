package cn.iocoder.yudao.module.validation.service.reportdata;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.validation.controller.admin.reportdata.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdata.ReportDataDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 报表数据 Service 接口
 *
 * @author 钟子明
 */
public interface ReportDataService {

    /**
     * 创建报表数据
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportData(@Valid ReportDataSaveReqVO createReqVO);

    /**
     * 更新报表数据
     *
     * @param updateReqVO 更新信息
     */
    void updateReportData(@Valid ReportDataSaveReqVO updateReqVO);

    /**
     * 删除报表数据
     *
     * @param id 编号
     */
    void deleteReportData(Long id);

    /**
    * 批量删除报表数据
    *
    * @param ids 编号
    */
    void deleteReportDataListByIds(List<Long> ids);

    /**
     * 获得报表数据
     *
     * @param id 编号
     * @return 报表数据
     */
    ReportDataDO getReportData(Long id);

    /**
     * 获得报表数据分页
     *
     * @param pageReqVO 分页查询
     * @return 报表数据分页
     */
    PageResult<ReportDataDO> getReportDataPage(ReportDataPageReqVO pageReqVO);

    /**
     * 根据Ids获取报表数据
     * @param reportIds
     * @return
     */
    Map<Long, List<ReportDataDO>> getReportDataByReportIds(List<Long> reportIds);
}