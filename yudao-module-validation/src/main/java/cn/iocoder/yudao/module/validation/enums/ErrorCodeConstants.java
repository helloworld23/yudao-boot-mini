package cn.iocoder.yudao.module.validation.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * @author zhongziming
 */
public interface ErrorCodeConstants {
    ErrorCode REPORT_NOT_EXISTS = new ErrorCode(2_001_000_001, "报表定义不存在");
    ErrorCode REPORT_DEFINITION_NOT_EXISTS = new ErrorCode(2_002_000_001, "报表表样不存在");
    ErrorCode REPORT_RULES_NOT_EXISTS = new ErrorCode(2_003_000_001, "校验规则不存在");
}
