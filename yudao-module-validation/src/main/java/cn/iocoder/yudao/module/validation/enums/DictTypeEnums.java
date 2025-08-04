package cn.iocoder.yudao.module.validation.enums;

/**
 * description
 *
 * @author zhongziming 2025/07/31 14:54
 */
public enum DictTypeEnums {
    VALIDATION_VALUE_TYPE("validation_value_type", "值类型"),
    VALIDATION_COMPARE_TYPE("validation_compare_type", "比较类型"),
    VALIDATION_RULE_TYPE("validation_rule_type", "规则类型");

    public static final String DICT_VALIDATION_VALUE_TYPE = "validation_value_type";
    public static final String DICT_VALIDATION_COMPARE_TYPE = "validation_compare_type";
    public static final String DICT_VALIDATION_RULE_TYPE = "validation_rule_type";

    private final String code;
    private final String name;

    DictTypeEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
