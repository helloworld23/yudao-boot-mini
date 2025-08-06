package cn.iocoder.yudao.module.validation.engine.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 校验是否包含任意一个值
 * eg: containsAny(b, '合同编号', a.合同编号)
 * 表示：a 表的合同编号，是否在 b 表的合同编号中
 *
 * @author zhongziming 2025/08/06 14:14
 */
public class ContainsAnyFunction extends AbstractFunction {

    @Override
    public String getName() {
        return "containsAny";
    }

    @Override
    public AviatorObject call(Map<String, Object> env,
                              AviatorObject listObj,
                              AviatorObject fieldNameObj,
                              AviatorObject valueObj) {

        Object list = listObj.getValue(env);
        String fieldName = fieldNameObj.stringValue(env);
        Object value = valueObj.getValue(env);

        if (!(list instanceof List)) return AviatorBoolean.FALSE;

        List<?> records = (List<?>) list;

        for (Object obj : records) {
            if (!(obj instanceof Map)) continue;
            Map<?, ?> record = (Map<?, ?>) obj;
            if (Objects.equals(record.get(fieldName), value)) {
                return AviatorBoolean.TRUE;
            }
        }

        return AviatorBoolean.FALSE;
    }
}

