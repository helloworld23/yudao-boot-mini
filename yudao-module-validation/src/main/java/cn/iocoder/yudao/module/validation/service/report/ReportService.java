package cn.iocoder.yudao.module.validation.service.report;

import java.util.*;

import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import jakarta.validation.*;
import cn.iocoder.yudao.module.validation.controller.admin.report.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * 报表定义 Service 接口
 *
 * @author 钟子明
 */
@Transactional(rollbackFor = Exception.class)
public interface ReportService {

    /**
     * 创建报表定义
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReport(@Valid ReportSaveReqVO createReqVO);

    /**
     * 更新报表定义
     *
     * @param updateReqVO 更新信息
     */
    void updateReport(@Valid ReportSaveReqVO updateReqVO);

    /**
     * 删除报表定义
     *
     * @param id 编号
     */
    void deleteReport(Long id);

    /**
    * 批量删除报表定义
    *
    * @param ids 编号
    */
    void deleteReportListByIds(List<Long> ids);

    /**
     * 获得报表定义
     *
     * @param id 编号
     * @return 报表定义
     */
    ReportDO getReport(Long id);

    /**
     * 获得报表定义分页
     *
     * @param pageReqVO 分页查询
     * @return 报表定义分页
     */
    PageResult<ReportDO> getReportPage(ReportPageReqVO pageReqVO);

    /**
     * 根据id获取报表定义
     * @param ids
     * @return
     */
    List<ReportDO> getReportListByIds(Collection<Long> ids);

    /**
     * 获取指定编号的报表定义
     * @param ids
     * @return
     */
    default Map<Long, ReportDO> getReportMap(List<Long> ids) {
        List<ReportDO> list = getReportListByIds(ids);
        return CollectionUtils.convertMap(list, ReportDO::getId);
    }

    /**
     * 获取报表定义列表
     * @return
     */
    List<ReportDO> getReportList(Integer status);

    /**
     * 根据id获取名称
     * @param aLong
     * @return
     */
    default String getReportNameById(Long aLong) {
        ReportDO reportDO = getReport(aLong);
        return reportDO.getName();
    }
}