package cn.iocoder.yudao.module.validation.service.report;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.validation.controller.admin.report.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.validation.dal.mysql.report.ReportMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.validation.enums.ErrorCodeConstants.*;

/**
 * 报表定义 Service 实现类
 *
 * @author 钟子明
 */
@Service
@Validated
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportProcessor processor;

    @Resource
    private ReportMapper reportMapper;

    @Override
    public Long createReport(ReportSaveReqVO createReqVO) {
        // 插入
        ReportDO report = BeanUtils.toBean(createReqVO, ReportDO.class);
        reportMapper.insert(report);
        // 文件不为空时解析
        if(StringUtils.hasText(report.getFile())) {
            // 解析文件，获取表头，然后插入到report_definition表中
            processor.resolveReportFile(report);
        }
        // 返回
        return report.getId();
    }

    @Override
    public void updateReport(ReportSaveReqVO updateReqVO) {
        // 校验存在
        validateReportExists(updateReqVO.getId());
        // 更新
        ReportDO updateObj = BeanUtils.toBean(updateReqVO, ReportDO.class);
        reportMapper.updateById(updateObj);
    }

    @Override
    public void deleteReport(Long id) {
        // 校验存在
        validateReportExists(id);
        // 删除
        reportMapper.deleteById(id);
    }

    @Override
        public void deleteReportListByIds(List<Long> ids) {
        // 校验存在
        validateReportExists(ids);
        // 删除
        reportMapper.deleteByIds(ids);
        }

    private void validateReportExists(List<Long> ids) {
        List<ReportDO> list = reportMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(REPORT_NOT_EXISTS);
        }
    }

    private void validateReportExists(Long id) {
        if (reportMapper.selectById(id) == null) {
            throw exception(REPORT_NOT_EXISTS);
        }
    }

    @Override
    public ReportDO getReport(Long id) {
        return reportMapper.selectById(id);
    }

    @Override
    public PageResult<ReportDO> getReportPage(ReportPageReqVO pageReqVO) {
        return reportMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ReportDO> getReportList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return reportMapper.selectBatchIds(ids);
    }

}