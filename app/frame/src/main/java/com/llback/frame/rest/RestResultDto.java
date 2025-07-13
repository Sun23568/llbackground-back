package com.llback.frame.rest;

import com.llback.common.exception.ErrorCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * css @2024
 * Rest返回结果包装实现
 *
 * @author hex
 * @date 2023/11/15 14:42
 */
@Slf4j
@Value
class RestResultDto<T> implements RestResult<T> {

    /**
     * 数据
     */
    T data;

    /**
     * 错误码
     */
    String code;

    /**
     * 错误信息
     */
    String msg;

    /**
     * 是否成功
     */
    boolean ok;

    /**
     * 构造函数
     *
     * @param data 数据
     * @param code 错误码
     * @param msg  错误信息
     */
    RestResultDto(T data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
        this.ok = ErrorCode.OK.getCode().equals(code);
    }

    /**
     * 操作成功的空数据
     */
    static final RestResultDto<Object> OK_EMPTY = success(null);

    /**
     * 构建操作成功的结果
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 结果
     */
    static <T> RestResultDto<T> success(T data) {
        return new RestResultDto<>(data, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
    }

    /**
     * 构建操作失败的结果
     *
     * @param code 错误码
     * @param msg  错误信息
     * @param <T>  数据类型
     * @return 结果
     */
    static <T> RestResultDto<T> failed(String code, String msg) {
        return failed(code, msg, null);
    }

    /**
     * 构建操作失败的结果
     *
     * @param code 错误码
     * @param msg  错误信息
     * @param data 数据
     * @param <T>  数据类型
     * @return 结果
     */
    static <T> RestResultDto<T> failed(String code, String msg, T data) {
        log.error("RestResultDto.failed: code={}, msg={}, data={}", code, msg, data);
        return new RestResultDto<>(data, code, msg);
    }
}
