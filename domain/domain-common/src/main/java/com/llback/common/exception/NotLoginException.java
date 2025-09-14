package com.llback.common.exception;


/**
 * 未登录认证异常
 */
public class NotLoginException extends BizException {
    /**
     * 默认构造函数
     */
    public NotLoginException() {
        super(ErrorCode.NOT_LOGIN);
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public NotLoginException(String message) {
        super(ErrorCode.NOT_LOGIN, message);
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     * @param cause   异常
     */
    public NotLoginException(String message, Throwable cause) {
        super(ErrorCode.NOT_LOGIN, message, cause);
    }

    /**
     * 构造函数
     *
     * @param cause 异常
     */
    public NotLoginException(Throwable cause) {
        super(ErrorCode.NOT_LOGIN, cause);
    }
}
