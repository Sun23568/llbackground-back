package com.llback.common.exception;

import lombok.Getter;

/**
 * 系统级异常
 *
 * @author hex
 * @date 2022/12/9 18:35
 */
@Getter
public class SysException extends RuntimeException {

    /**
     * 错误码
     */
    private final ErrorCode error;

    /**
     * 构造函数
     *
     * @param errorCode 错误码
     */
    public SysException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.error = errorCode;
    }

    /**
     * 构造函数
     *
     * @param errorCode 错误码
     * @param cause     异常
     */
    public SysException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.error = errorCode;
    }

    /**
     * 构造函数
     *
     * @param errorCode 错误码
     * @param message   错误信息
     * @param cause     异常
     */
    public SysException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.formatMessage(message), cause);
        this.error = errorCode;
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public SysException(String message) {
        super(ErrorCode.SERVICE_INNER_ERROR.formatMessage(message));
        this.error = ErrorCode.SERVICE_INNER_ERROR;
    }

    /**
     * 构造函数
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public SysException(ErrorCode errorCode, String message) {
        super(errorCode.formatMessage(message));
        this.error = errorCode;
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     * @param cause   异常
     */
    public SysException(String message, Throwable cause) {
        super(ErrorCode.SERVICE_INNER_ERROR.formatMessage(message), cause);
        this.error = ErrorCode.SERVICE_INNER_ERROR;
    }
}
