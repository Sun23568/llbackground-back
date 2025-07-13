package com.llback.common.exception;

import com.llback.common.util.AssertUtil;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * 错误码定义
 *
 * @author 作者hex
 * @date 2022/6/30 11:33 下午
 */
@Getter
@ToString
public final class ErrorCode implements Serializable {

    /**
     * 错误码集合
     */
    private static final LinkedHashMap<String, ErrorCode> ERROR_MAP = new LinkedHashMap<>(200);

    /**
     * OK
     */
    public static final ErrorCode OK = registerErrorCode("000000", "ok", 200);

    /**
     * 一般性业务警告
     */
    public static final ErrorCode GENERIC_BIZ_WARN = registerErrorCode("101000", "%s", 200);

    /**
     * 一般性业务错误
     */
    public static final ErrorCode GENERIC_BIZ_ERROR = registerErrorCode("102000", "%s", 200);

    /**
     * 用户登录认证失败（验证码等校验失败）
     */
    public static final ErrorCode LOGIN_USER_VERIFY_FAILED = registerErrorCode("102101", "登录认证失败", 200);

    /**
     * 用户登录认证失败（密码错误）
     */
    public static final ErrorCode LOGIN_USER_AUTH_FAILED = registerErrorCode("102110", "登录认证失败，用户名或密码错误", 200);

    /**
     * 用户登录认证失败（密码错误）
     */
    public static final ErrorCode LOGIN_USER_AUTH_MOBILE_FAILED = registerErrorCode("102111", "登录认证失败，手机号或密码错误", 200);

    /**
     * 用户登录认证失败（模糊提示）
     */
    public static final ErrorCode LOGIN_USER_AUTH_MOBILE_OR_SMS_CODE_FAILED = registerErrorCode("102112", "登录认证失败，账号、密码或验证码错误", 200);

    /**
     * 用户登录认证失败（自动锁定）
     */
    public static final ErrorCode LOGIN_USER_AUTO_LOCKED = registerErrorCode("102100", "用户已锁定，请联系系统管理员解锁", 200);

    /**
     * 用户登录认证失败（停用）
     */
    public static final ErrorCode LOGIN_USER_DEACTIVATED = registerErrorCode("102130", "用户已停用，请联系系统管理员", 200);

    /**
     * 用户未审核
     */
    public static final ErrorCode LOGIN_USER_UN_REVIEW = registerErrorCode("102131", "用户未审核通过，禁止登录", 200);

    /**
     * 用户状态不允许登录
     */
    public static final ErrorCode LOGIN_USER_DISABLED = registerErrorCode("102120", "禁止登录", 200);


    /**
     * 无效的输入参数
     */
    public static final ErrorCode INVALID_PARAM = registerErrorCode("102400", "[%s]");

    /**
     * 未登录认证
     */
    public static final ErrorCode NOT_LOGIN = registerErrorCode("102401", "未登录认证", 401);

    /**
     * 用户未激活
     */
    public static final ErrorCode NOT_ACTIVATION = registerErrorCode("102402", "用户未激活", 200);


    /**
     * 没有操作权限
     */
    public static final ErrorCode FORBID = registerErrorCode("102403", "未授权访问:【%s】", 403);

    /**
     * 用户已绑定微信公众号
     */
    public static final ErrorCode BINDED_WEI_XIN=registerErrorCode("102404", "该用户已绑定微信公众号", 200);


    /**
     * 资源不存在
     */
    public static final ErrorCode NOT_FOUND = registerErrorCode("103404", "未找到指定的资源: %s", 404);

    /***
     * 系统内部错误
     */
    public static final ErrorCode SERVICE_INNER_ERROR = registerErrorCode("103500", "系统内部错误, 请稍后再试!", 500);

    /**
     * 查询数量超过系统限制
     */
    public static final ErrorCode QUERY_EXCEED_LIMIT = registerErrorCode("103503", "系统繁忙, 访问超过限制!", 503);

    /**
     * 请求重复
     */
    public static final ErrorCode REQUEST_REPLAY_FORBIDDEN = registerErrorCode("102405", "请求重复", 409);

    /**
     * 验证签名失败
     */
    public static final ErrorCode FAILED_VERIFY_SIGN = registerErrorCode("104403", "非法的数据签名", 403);

    /**
     * 非法的文件内容
     */
    public static final ErrorCode FILE_SECURITY_ERROR = registerErrorCode("105400", "非法的文件内容", 400);

    /**
     * 微信调用异常
     */
    public static final ErrorCode WEI_XIN_ERROR = registerErrorCode("106500", "微信调用异常", 500);

    /**
     * 文件组件调用异常
     */
    public static final ErrorCode FILE_UTIL_ERROR = registerErrorCode("107500", "%s", 500);

    /**
     * 文件上传失败
     */
    public static final ErrorCode FILE_UPLOAD_ERROR = registerErrorCode("105401", "文件上传失败", 400);

    /**
     * 文件下载失败
     */
    public static final ErrorCode FILE_DOWNLOAD_ERROR = registerErrorCode("105402", "文件下载失败", 400);

    /**
     * 流水回单下载超时
     */
    public static final ErrorCode FLOW_RECEIPT_TIMEOUT_ERROR = registerErrorCode("105701", "流水回单下载超时", 400);


    /**
     * 错误码
     */
    private final String code;


    /**
     * 错误信息
     */
    @Getter
    private final String message;


    /**
     * httpCode
     */
    @Getter
    private final int httpCode;


    /**
     * 格式化错误信息
     *
     * @param input input
     * @return String
     */
    public String formatMessage(String input) {
        return this.message.contains("%s") ? String.format(this.message, input) : this.message + ":" + input;
    }


    /**
     * 注册
     *
     * @return ErrorCode
     */
    private ErrorCode registerToMap() {
        AssertUtil.assertFalse(ERROR_MAP.containsKey(this.code), "重复注册错误码:" + this.code);
        ERROR_MAP.put(this.getCode(), this);
        return this;
    }

    /**
     * find by code
     *
     * @param code errorCode
     * @return ErrorCode
     */
    public static ErrorCode codeOf(String code) {
        ErrorCode error = ERROR_MAP.get(code);
        AssertUtil.notNull(error, "not found error code:" + code);
        return error;
    }

    /**
     * hashcode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return this.code.hashCode();
    }

    /**
     * equals
     *
     * @param obj obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ErrorCode) {
            return this.code.equals(((ErrorCode) obj).code);
        }
        return false;
    }

    /**
     * ErrorCode
     *
     * @param code     code
     * @param message  message
     * @param httpCode http status code
     */
    ErrorCode(String code, String message, int httpCode) {
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }

    /**
     * 是否为系统级错误
     *
     * @return boolean
     */
    public boolean isSysError() {
        return this.httpCode >= 500;
    }


    /**
     * 抛出异常
     */
    public void throwError() {
    }

    /**
     * 抛出异常
     *
     * @param message message
     */
    public void throwError(String message) {

    }

    /**
     * 抛出异常
     *
     * @param message message
     * @param cause   cause
     */
    public void throwError(String message, Throwable cause) {

    }

    /**
     * 抛出异常
     *
     * @param cause cause
     */
    public void throwError(Throwable cause) {

    }


    /**
     * 注册错误码
     *
     * @param code     code
     * @param message  message
     * @param httpCode http status code
     * @return ErrorCode
     */
    public static ErrorCode registerErrorCode(String code, String message, int httpCode) {
        return new ErrorCode(code, message, httpCode).registerToMap();
    }


    /**
     * 注册错误码(httpCode=200)
     *
     * @param code    code
     * @param message message
     * @return ErrorCode
     */
    public static ErrorCode registerErrorCode(String code, String message) {
        return registerErrorCode(code, message, 200);
    }


    /**
     * 获得所有错误码表
     *
     * @return Collection<ErrorCode>
     */
    public static Collection<ErrorCode> getErrorList() {
        return Collections.unmodifiableCollection(ERROR_MAP.values());
    }

    /**
     * 是否为ok
     *
     * @return boolean
     */
    public boolean isOk() {
        return this == ErrorCode.OK;
    }
}
