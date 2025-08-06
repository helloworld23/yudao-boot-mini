package cn.iocoder.yudao.module.validation.engine.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * eg: sumByMatch(a, '合同编号', b.合同编号, '金额')
 * 表示：根据 a 表的合同编号，在 b 表中查找符合条件的行，然后计算这些行的金额字段的总和
 */
public class SumByMatchFunction extends AbstractFunction {
    @Override
    public String getName() {
        return "sumByMatch";
    }

    /**
     * 计算符合条件的行的字段值的总和
     * @param env 参数
     * @param dataObj 需要汇总的子表数据
     * @param matchKeyFieldObj 汇总数据的子表匹配字段
     * @param matchValueObj 主表匹配字段
     * @param sumFieldObj 子表需要汇总的字段
     * @return 子表符合条件的行的字段值的总和
     */
    @Override
    public AviatorObject call(Map<String, Object> env,
                              AviatorObject dataObj,
                              AviatorObject matchKeyFieldObj,
                              AviatorObject matchValueObj,
                              AviatorObject sumFieldObj) {

        List<Map<String, Object>> data = (List<Map<String, Object>>) dataObj.getValue(env);
        String matchKeyField = matchKeyFieldObj.stringValue(env);
        Object matchValue = matchValueObj.getValue(env);
        String sumField = sumFieldObj.stringValue(env);

        double total = 0.0;
        for (Map<String, Object> row : data) {
            if (matchValue != null && Objects.equals(row.get(matchKeyField), matchValue)) {
                Object val = row.get(sumField);
                if (val instanceof String) {
                    val = Double.parseDouble((String) val);
                }
                if (val instanceof Number) {
                    total += ((Number) val).doubleValue();
                }
            }
        }
        return new AviatorDouble(total);
    }
}
