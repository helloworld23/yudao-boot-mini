package cn.iocoder.yudao.module.validation.service.reportdefinition;

import java.util.*;

import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.module.validation.dal.dataobject.report.ReportDO;
import jakarta.validation.*;
import cn.iocoder.yudao.module.validation.controller.admin.reportdefinition.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdefinition.ReportDefinitionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 报表表样 Service 接口
 *
 * @author 钟子明
 */
public interface ReportDefinitionService {

    /**
     * 创建报表表样
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportDefinition(@Valid ReportDefinitionSaveReqVO createReqVO);

    /**
     * 更新报表表样
     *
     * @param updateReqVO 更新信息
     */
    void updateReportDefinition(@Valid ReportDefinitionSaveReqVO updateReqVO);

    /**
     * 删除报表表样
     *
     * @param id 编号
     */
    void deleteReportDefinition(Long id);

    /**
    * 批量删除报表表样
    *
    * @param ids 编号
    */
    void deleteReportDefinitionListByIds(List<Long> ids);

    /**
     * 获得报表表样
     *
     * @param id 编号
     * @return 报表表样
     */
    ReportDefinitionDO getReportDefinition(Long id);

    /**
     * 获得报表表样分页
     *
     * @param pageReqVO 分页查询
     * @return 报表表样分页
     */
    PageResult<ReportDefinitionDO> getReportDefinitionPage(ReportDefinitionPageReqVO pageReqVO);

    /**
     * 根据id获取报表定义
     * @param ids
     * @return
     */
    List<ReportDefinitionDO> getReportDefinitionList(Collection<Long> ids);

    /**
     * 获取指定编号的字段定义
     * @param ids
     * @return
     */
    default Map<Long, ReportDefinitionDO> getReportDefinitionMap(List<Long> ids) {
        List<ReportDefinitionDO> list = getReportDefinitionList(ids);
        return CollectionUtils.convertMap(list, ReportDefinitionDO::getId);
    }

    List<ReportDefinitionDO> getReportDefinitionByReportId(Long id);
}