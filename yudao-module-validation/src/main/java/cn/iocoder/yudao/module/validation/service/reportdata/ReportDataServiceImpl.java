package cn.iocoder.yudao.module.validation.service.reportdata;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.validation.controller.admin.reportdata.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdata.ReportDataDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.validation.dal.mysql.reportdata.ReportDataMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.validation.enums.ErrorCodeConstants.*;

/**
 * 报表数据 Service 实现类
 *
 * @author 钟子明
 */
@Service
@Validated
public class ReportDataServiceImpl implements ReportDataService {

    @Resource
    private ReportDataMapper reportDataMapper;

    @Override
    public Long createReportData(ReportDataSaveReqVO createReqVO) {
        // 插入
        ReportDataDO reportData = BeanUtils.toBean(createReqVO, ReportDataDO.class);
        reportDataMapper.insert(reportData);
        // 返回
        return reportData.getId();
    }

    @Override
    public void updateReportData(ReportDataSaveReqVO updateReqVO) {
        // 校验存在
        validateReportDataExists(updateReqVO.getId());
        // 更新
        ReportDataDO updateObj = BeanUtils.toBean(updateReqVO, ReportDataDO.class);
        reportDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportData(Long id) {
        // 校验存在
        validateReportDataExists(id);
        // 删除
        reportDataMapper.deleteById(id);
    }

    @Override
        public void deleteReportDataListByIds(List<Long> ids) {
        // 校验存在
        validateReportDataExists(ids);
        // 删除
        reportDataMapper.deleteByIds(ids);
        }

    private void validateReportDataExists(List<Long> ids) {
        List<ReportDataDO> list = reportDataMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(REPORT_DATA_NOT_EXISTS);
        }
    }

    private void validateReportDataExists(Long id) {
        if (reportDataMapper.selectById(id) == null) {
            throw exception(REPORT_DATA_NOT_EXISTS);
        }
    }

    @Override
    public ReportDataDO getReportData(Long id) {
        return reportDataMapper.selectById(id);
    }

    @Override
    public PageResult<ReportDataDO> getReportDataPage(ReportDataPageReqVO pageReqVO) {
        return reportDataMapper.selectPage(pageReqVO);
    }

    @Override
    public Map<Long, List<ReportDataDO>> getReportDataByReportIds(List<Long> reportIds) {
        return reportDataMapper.getReportDataByReportIds(reportIds);
    }

}