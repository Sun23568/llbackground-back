package com.llback.common.exception;

/**
 * @author hex  2024/4/2 08:47
 */
public interface BizError {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    ErrorCode getError();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMessage();
}
