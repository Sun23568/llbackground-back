package com.llback.frame.rest;


import com.llback.common.exception.BizError;
import com.llback.common.exception.ErrorCode;

/**
 * css @2024
 * Rest返回结果的接口定义
 *
 * @author hex
 * @date 2023/11/15 14:42
 */
public interface RestResult<T> {
    /**
     * 操作结果数据
     *
     * @return T
     * @mock null
     */
    T getData();

    /**
     * 操作返回码
     *
     * @return String
     * @mock 000000
     */
    String getCode();

    /**
     * 操作返回信息
     *
     * @return String
     * @mock 操作成功
     */
    String getMsg();

    /**
     * 判断操作是否成功
     *
     * @return boolean
     */
    default boolean ok() {
        return ErrorCode.OK.getCode().equals(this.getCode());
    }

    /**
     * 包装操作成功的返回结果
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return RestResult
     */
    static <T> RestResult<T> ok(T data) {
        return RestResultDto.success(data);
    }

    /**
     * 包装操作失败的返回结果
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return RestResult
     */
    static RestResult<Object> failed(ErrorCode code, String msg) {
        return failed(code, msg, null);
    }

    /**
     * 包装操作失败的返回结果
     *
     * @param code 错误码
     * @param msg  错误信息
     * @param data 数据
     * @return RestResult
     */
    static RestResult<Object> failed(ErrorCode code, String msg, Object data) {
        return RestResultDto.failed(code.getCode(), code.formatMessage(msg), data);
    }

    /**
     * 包装操作失败的返回结果
     *
     * @param error 错误信息
     */
    static RestResult<Object> failed(ErrorCode error) {
        return RestResultDto.failed(error.getCode(), error.getMessage());
    }

    /**
     * 包装操作失败的返回结果
     *
     * @param error 错误信息
     * @param data  数据
     */
    static RestResult<Object> failed(ErrorCode error, Object data) {
        return RestResultDto.failed(error.getCode(), error.getMessage(), data);
    }

    /**
     * 包装操作失败的返回结果
     *
     * @param e 错误信息
     */
    static RestResult<Object> failed(BizError e) {
        return RestResultDto.failed(e.getError().getCode(), e.getMessage());
    }


    /**
     * 包装操作失败的返回结果
     *
     * @param e    错误信息
     * @param data 数据
     */
    static RestResult<Object> failed(BizError e, Object data) {
        return RestResultDto.failed(e.getError().getCode(), e.getMessage(), data);
    }
}
