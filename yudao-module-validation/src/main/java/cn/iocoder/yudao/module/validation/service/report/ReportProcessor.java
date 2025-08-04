package cn.iocoder.yudao.module.validation.service.report;

import cn.hutool.core.collection.CollectionUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.module.validation.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdata.ReportDataDO;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdefinition.ReportDefinitionDO;
import cn.iocoder.yudao.module.validation.dal.mysql.reportdata.ReportDataMapper;
import cn.iocoder.yudao.module.validation.dal.mysql.reportdefinition.ReportDefinitionMapper;
import cn.iocoder.yudao.module.validation.service.reportdefinition.ReportDefinitionService;
import cn.iocoder.yudao.module.validation.util.EncoderUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 单独拆出一个处理类，避免事务失效、处理事务逻辑复杂。
 *
 * @author zhongziming 2025/07/24 20:35
 */
@Slf4j
@Service
public class ReportProcessor {

    @Resource
    private ReportDefinitionService reportDefinitionService;

    @Resource
    private ReportDefinitionMapper reportDefinitionMapper;

    @Resource
    private ReportDataMapper reportDataMapper;

    // 主入口方法
    @Transactional(rollbackFor = Exception.class)
    public void resolveReportFile(ReportDO report) {
        if (!StringUtils.hasText(report.getFile())) {
            return;
        }
        String finalUrl = EncoderUtil.encodeFileUrl(report.getFile());
        try (InputStream inputStream = new URL(finalUrl).openStream()) {
            AtomicReference<Map<Integer, String>> firstRowRef = new AtomicReference<>();
            List<Map<Integer, String>> dataRows = new ArrayList<>();
            AtomicBoolean isFirst = new AtomicBoolean(true);
            EasyExcel.read(inputStream, new AnalysisEventListener<Map<Integer, String>>() {
                @Override
                public void invoke(Map<Integer, String> data, AnalysisContext context) {
                    if (isFirst.get()) {
                        firstRowRef.set(data);
                        isFirst.set(false);
                    } else {
                        dataRows.add(data);
                    }
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                }
            }).sheet(0).headRowNumber(0).doRead();
            Map<Integer, String> head = firstRowRef.get();
            if (CollectionUtil.isNotEmpty(head)) {
                parseReportDefinition(report, head);
            }
            List<ReportDefinitionDO> columnDefs = reportDefinitionService.getReportDefinitionByReportId(report.getId());
            if (!dataRows.isEmpty()) {
                parseReportData(report, dataRows, columnDefs);
            }

        } catch (Exception e) {
            log.error("读取报表文件失败：{}", e.getMessage(), e);
        }
    }

    /**
     * 解析报表表头
     * @param report
     * @param head
     */
    private void parseReportDefinition(ReportDO report, Map<Integer, String> head) {
        List<ReportDefinitionDO> definitionList = head.entrySet().stream()
                .map(entry -> {
                    ReportDefinitionDO def = new ReportDefinitionDO();
                    def.setName(entry.getValue());
                    def.setReportId(report.getId());
                    def.setColumnIndex(entry.getKey());
                    def.setStatus(CommonStatusEnum.ENABLE.getStatus());
                    return def;
                }).collect(Collectors.toList());
        reportDefinitionMapper.insertBatch(definitionList);
        log.info("保存字段定义 {} 条", definitionList.size());
    }

    /**
     * 解析报表数据
     * @param report
     * @param dataRows
     * @param columnDefs
     */
    private void parseReportData(ReportDO report, List<Map<Integer, String>> dataRows, List<ReportDefinitionDO> columnDefs) {
        // 构建 columnIndex -> columnId 映射
        Map<Integer, Long> columnIndexToId = columnDefs.stream()
                .collect(Collectors.toMap(ReportDefinitionDO::getColumnIndex, ReportDefinitionDO::getId));

        List<ReportDataDO> cellList = new ArrayList<>();
        for (int i = 0; i < dataRows.size(); i++) {
            Map<Integer, String> row = dataRows.get(i);
            for (Map.Entry<Integer, String> entry : row.entrySet()) {
                ReportDataDO cell = new ReportDataDO();
                cell.setReportId(report.getId());
                cell.setRowIndex(i);
                cell.setColumnIndex(entry.getKey());
                cell.setColumnId(columnIndexToId.get(entry.getKey()));
                cell.setValue(entry.getValue());
                cellList.add(cell);
            }
        }

        reportDataMapper.insertBatch(cellList);
        log.info("共生成报表单元格数据 {} 条", cellList.size());
    }


}

