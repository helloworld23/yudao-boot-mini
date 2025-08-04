package cn.iocoder.yudao.module.validation.service.reportdefinition;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.validation.controller.admin.reportdefinition.vo.*;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdefinition.ReportDefinitionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.validation.dal.mysql.reportdefinition.ReportDefinitionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.validation.enums.ErrorCodeConstants.*;

/**
 * 报表表样 Service 实现类
 *
 * @author 钟子明
 */
@Service
@Validated
public class ReportDefinitionServiceImpl implements ReportDefinitionService {

    @Resource
    private ReportDefinitionMapper reportDefinitionMapper;

    @Override
    public Long createReportDefinition(ReportDefinitionSaveReqVO createReqVO) {
        // 插入
        ReportDefinitionDO reportDefinition = BeanUtils.toBean(createReqVO, ReportDefinitionDO.class);
        reportDefinitionMapper.insert(reportDefinition);
        // 返回
        return reportDefinition.getId();
    }

    @Override
    public void updateReportDefinition(ReportDefinitionSaveReqVO updateReqVO) {
        // 校验存在
        validateReportDefinitionExists(updateReqVO.getId());
        // 更新
        ReportDefinitionDO updateObj = BeanUtils.toBean(updateReqVO, ReportDefinitionDO.class);
        reportDefinitionMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportDefinition(Long id) {
        // 校验存在
        validateReportDefinitionExists(id);
        // 删除
        reportDefinitionMapper.deleteById(id);
    }

    @Override
        public void deleteReportDefinitionListByIds(List<Long> ids) {
        // 校验存在
        validateReportDefinitionExists(ids);
        // 删除
        reportDefinitionMapper.deleteByIds(ids);
        }

    private void validateReportDefinitionExists(List<Long> ids) {
        List<ReportDefinitionDO> list = reportDefinitionMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(REPORT_DEFINITION_NOT_EXISTS);
        }
    }

    private void validateReportDefinitionExists(Long id) {
        if (reportDefinitionMapper.selectById(id) == null) {
            throw exception(REPORT_DEFINITION_NOT_EXISTS);
        }
    }

    @Override
    public ReportDefinitionDO getReportDefinition(Long id) {
        return reportDefinitionMapper.selectById(id);
    }

    @Override
    public PageResult<ReportDefinitionDO> getReportDefinitionPage(ReportDefinitionPageReqVO pageReqVO) {
        return reportDefinitionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ReportDefinitionDO> getReportDefinitionList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return reportDefinitionMapper.selectList(
                new LambdaQueryWrapper<ReportDefinitionDO>()
                        .in(ReportDefinitionDO::getReportId, ids)
                        .eq(ReportDefinitionDO::getStatus, CommonStatusEnum.ENABLE.getStatus())
        );
    }

    @Override
    public List<ReportDefinitionDO> getReportDefinitionByReportId(Long id) {
        return reportDefinitionMapper.selectByReportId(id);
    }

}