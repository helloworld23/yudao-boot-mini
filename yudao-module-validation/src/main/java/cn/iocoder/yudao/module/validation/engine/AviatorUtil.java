package cn.iocoder.yudao.module.validation.engine;

import cn.iocoder.yudao.module.validation.dal.dataobject.reportdata.ReportDataDO;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdefinition.ReportDefinitionDO;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportrule.ReportRuleDO;
import cn.iocoder.yudao.module.validation.engine.function.ContainsAnyFunction;
import cn.iocoder.yudao.module.validation.engine.function.NumberFunction;
import cn.iocoder.yudao.module.validation.engine.function.SumByMatchFunction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public final class AviatorUtil {

    private AviatorUtil() {}

    static {
        AviatorEvaluator.addFunction(new SumByMatchFunction());
        AviatorEvaluator.addFunction(new NumberFunction());
        AviatorEvaluator.addFunction(new ContainsAnyFunction());
    }

    public static Map<String, List<Map<String, Object>>> buildParamByRule(
            ReportRuleDO reportRule,
            Map<Long, List<ReportDataDO>> dataMap,
            Function<Long, List<ReportDefinitionDO>> reportDefinitionLoader,
            Function<Long, String> reportNameLoader) {

        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Long> tableIds;
        try {
            tableIds = new ObjectMapper().readValue(reportRule.getInvolvedTables(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("involvedTables JSON 解析失败: " + reportRule.getInvolvedTables(), e);
        }

        if (CollectionUtils.isEmpty(tableIds)) {
            return result;
        }

        if (tableIds.size() == 1) {
            Long reportId = tableIds.get(0);
            String reportName = reportNameLoader.apply(reportId);
            List<Map<String, Object>> rows = convertReportTable(reportId, dataMap, reportDefinitionLoader);
            result.put(reportName, rows);

        } else if (tableIds.size() == 2) {
            Long firstId = tableIds.get(0);
            Long secondId = tableIds.get(1);

            List<Map<String, Object>> firstTable = convertReportTable(firstId, dataMap, reportDefinitionLoader);
            List<Map<String, Object>> secondTable = convertReportTable(secondId, dataMap, reportDefinitionLoader);

            List<Map<String, Object>> pairs = new ArrayList<>();
            for (Map<String, Object> a : firstTable) {
                Map<String, Object> pair = new HashMap<>();
                pair.put("a", a);
                pair.put("b", secondTable);
                pairs.add(pair);
            }

            String key = reportNameLoader.apply(firstId) + "_" + reportNameLoader.apply(secondId);
            result.put(key, pairs);

        } else {
            throw new UnsupportedOperationException("暂不支持多于两个表的规则");
        }

        return result;
    }

    private static List<Map<String, Object>> convertReportTable(
            Long reportId,
            Map<Long, List<ReportDataDO>> dataMap,
            Function<Long, List<ReportDefinitionDO>> reportDefinitionLoader) {

        List<ReportDataDO> dataList = dataMap.getOrDefault(reportId, Collections.emptyList());
        List<ReportDefinitionDO> columnDefs = reportDefinitionLoader.apply(reportId);

        Map<Integer, String> indexToName = columnDefs.stream()
                .collect(Collectors.toMap(ReportDefinitionDO::getColumnIndex, ReportDefinitionDO::getName));

        Map<Integer, List<ReportDataDO>> rowGrouped = dataList.stream()
                .collect(Collectors.groupingBy(ReportDataDO::getRowIndex));

        return rowGrouped.values().stream()
                .map(row -> {
                    Map<String, Object> rowMap = new LinkedHashMap<>();
                    for (ReportDataDO cell : row) {
                        String colName = indexToName.get(cell.getColumnIndex());
                        if (colName != null) {
                            rowMap.put(colName, cell.getValue());
                        }
                    }
                    return rowMap;
                })
                .collect(Collectors.toList());
    }

    public static boolean validate(ReportRuleDO reportRule, Map<String, List<Map<String, Object>>> params) {
        if (params.size() == 1) {
            List<Map<String, Object>> rows = params.values().iterator().next();
            for (Map<String, Object> row : rows) {
                executeRule(reportRule, row);
            }
        } else if (params.size() == 2) {
            // 双表校验，取出 a、b
            Iterator<Map.Entry<String, List<Map<String, Object>>>> iterator = params.entrySet().iterator();
            Map.Entry<String, List<Map<String, Object>>> entryA = iterator.next();
            Map.Entry<String, List<Map<String, Object>>> entryB = iterator.next();

            List<Map<String, Object>> aList = entryA.getValue();
            List<Map<String, Object>> bList = entryB.getValue();

            for (Map<String, Object> aRow : aList) {
                Map<String, Object> env = new HashMap<>();
                env.put("a", aRow);
                env.put("b", bList);
                executeRule(reportRule, env);
            }
        }
        return true;
    }

    public static boolean executeRule(ReportRuleDO reportRule, Map<String, Object> row) {
        if (StringUtils.hasText(reportRule.getConditionExpression()) && !execute(reportRule.getConditionExpression(), row)) {
            log.info("未满足过滤条件，跳过此行数据，条件表达式为：{}", reportRule.getConditionExpression());
            return true;
        }

        if (StringUtils.hasText(reportRule.getRuleLogic()) && !execute(reportRule.getRuleLogic(), row)) {
            log.info("数据校验不通过，校验表达式为：{}", reportRule.getRuleLogic());
            return false;
        }
        return true;
    }

    public static boolean execute(String ruleLogic, Map<String, Object> row) {
        Expression expression = AviatorEvaluator.compile(ruleLogic, true);
        for (String var : expression.getVariableNames()) {
            Object value = resolveNestedValue(var, row);
            log.info("变量名: {}, 值: {}", var, value);
        }
        Object result = expression.execute(row);
        log.info("表达式为：{}，表达式结果: {}", ruleLogic, result);
        return (boolean) result;
    }

    private static Object resolveNestedValue(String var, Map<String, Object> context) {
        if (!var.contains(".")) return context.get(var);
        String[] parts = var.split("\\.");
        Object current = context.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            if (current instanceof Map) {
                current = ((Map<?, ?>) current).get(parts[i]);
            } else {
                return null;
            }
        }
        return current;
    }
}
