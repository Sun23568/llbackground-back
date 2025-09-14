package com.llback.common.exception;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author hex
 * @date 2022/12/9 18:36
 */
@Getter
public class BizException extends RuntimeException implements BizError {

    /**
     * 错误码
     */
    private final ErrorCode error;


    /**
     * 构造函数
     *
     * @param errorCode 错误码
     */
    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.error = errorCode;
    }

    /**
     * 构造函数
     *
     * @param errorCode 错误码
     * @param cause     异常
     */
    public BizException(ErrorCode errorCode, Throwable cause) {
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
    public BizException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.formatMessage(message), cause);
        this.error = errorCode;
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public BizException(String message) {
        super(ErrorCode.GENERIC_BIZ_ERROR.formatMessage(message));
        this.error = ErrorCode.GENERIC_BIZ_ERROR;
    }

    /**
     * 构造函数
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public BizException(ErrorCode errorCode, String message) {
        super(errorCode.formatMessage(message));
        this.error = errorCode;
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     * @param cause   异常
     */
    public BizException(String message, Throwable cause) {
        super(ErrorCode.GENERIC_BIZ_ERROR.formatMessage(message), cause);
        this.error = ErrorCode.GENERIC_BIZ_ERROR;
    }
}
