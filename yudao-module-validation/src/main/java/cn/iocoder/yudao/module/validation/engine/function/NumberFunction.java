package cn.iocoder.yudao.module.validation.engine.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorDouble;

import java.util.Map;

/**
 * 类型转换
 * @author zhongziming
 */
public class NumberFunction extends AbstractFunction {

    @Override
    public String getName() {
        return "number";
    }

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg) {
        Object val = arg.getValue(env);
        if (val instanceof Number) {
            return new AviatorDouble(((Number) val).doubleValue());
        }
        if (val instanceof String) {
            try {
                return new AviatorDouble(Double.parseDouble((String) val));
            } catch (NumberFormatException e) {
                return new AviatorDouble(0); // 或抛异常根据需求
            }
        }
        return new AviatorDouble(0);
    }
}

