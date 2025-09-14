package com.llback.common.exception;


/**
 * 403 禁止访问异常
 */
public class ForbidException extends BizException {

    /**
     * 默认构造函数
     */
    public ForbidException() {
        super(ErrorCode.FORBID);
    }

    /**
     * 构造函数(使用自定义异常码)
     * @param errorCode
     */
    public ForbidException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 构造函数(补充异常信息）
     *
     * @param message 异常信息
     */
    public ForbidException(String message) {
        super(ErrorCode.FORBID, message);
    }

    /**
     * 构造函数(补充异常信息）
     *
     * @param message 异常信息
     * @param cause   异常
     */
    public ForbidException(String message, Throwable cause) {
        super(ErrorCode.FORBID, message, cause);
    }

    /**
     * 构造函数(补充异常信息）
     *
     * @param cause 异常
     */
    public ForbidException(Throwable cause) {
        super(ErrorCode.FORBID, cause);
    }
}
