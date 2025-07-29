package cn.iocoder.yudao.module.validation.service.report;

import cn.hutool.core.collection.CollectionUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.module.validation.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdefinition.ReportDefinitionDO;
import cn.iocoder.yudao.module.validation.dal.mysql.reportdefinition.ReportDefinitionMapper;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 单独拆出一个处理类，避免事务失效、处理事务逻辑复杂。
 *
 * @author zhongziming 2025/07/24 20:35
 */
@Service
@Slf4j
public class ReportProcessor {

    @Resource
    private ReportDefinitionMapper reportDefinitionMapper;

    @Transactional(rollbackFor = Exception.class)
    public void resolveReportFile(ReportDO report) {
        if(StringUtils.hasText(report.getFile())) {
            // 根据file路径，先获取文件内容
            Map<Integer, String> head = readFirstRowAsMap(report.getFile());
            if(CollectionUtil.isNotEmpty(head)) {
                // 遍历head，根据head的key，获取对应的value，然后插入到report_definition表中
                List<ReportDefinitionDO> list = head.entrySet().stream()
                        .map(entry -> {
                            ReportDefinitionDO def = new ReportDefinitionDO();
                            def.setName(entry.getValue());
                            def.setReportId(report.getId());
                            def.setColumnIndex(entry.getKey());
                            def.setStatus(CommonStatusEnum.ENABLE.getStatus());
                            return def;
                        }).collect(Collectors.toList());
                reportDefinitionMapper.insertBatch(list);
            }
        }

    }

    /**
     * 获取表头
     */
    public static Map<Integer, String> readFirstRowAsMap(String fileUrl) {
        // 定义个集合，方便塞值
        AtomicReference<Map<Integer, String>> firstRow = new AtomicReference<>();
        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            // easyexcel api，不懂就百度，很简单的。
            EasyExcel.read(inputStream, new AnalysisEventListener<Map<Integer, String>>() {
                @Override
                public void invoke(Map<Integer, String> data, AnalysisContext context) {
                    log.info("获取 {} 表格，第一行数据为：{}", fileUrl, data);
                    firstRow.set(data);
                    // 我也是无语，都标识丢弃了，又不更新方法，你倒是告诉我应该用啥中断
                    context.interrupt();
                }
                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {}
            }).sheet(0).headRowNumber(0).doRead();
        } catch (Exception ignored) {

        }
        return firstRow.get();
    }

}
