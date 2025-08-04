package cn.iocoder.yudao.module.validation;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author zhongziming 2025/08/01 13:09
 */
public class AviatorDemo {
    public static void main(String[] args) {
        // 示例数据
        Map<String, Object> env = new HashMap<>();
        env.put("age", 25);
        env.put("name", "Alice");

        // 简单表达式
        String expression = "age > 18 && name == 'Alice'";

        // 执行表达式
        Boolean result = (Boolean) AviatorEvaluator.execute(expression, env);

        System.out.println("表达式结果：" + result);
    }
}
