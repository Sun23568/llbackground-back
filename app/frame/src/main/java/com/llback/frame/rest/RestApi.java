package com.llback.frame.rest;


import com.llback.frame.AppFrame;
import com.llback.frame.dto.Command;
import com.llback.frame.dto.Query;

/**
 * css @2024
 * RestApi 接口，提供了CQRS执行方法
 *
 * @author hex
 * @date 2023/11/22 14:44
 */
public interface RestApi {

    /**
     * 执行查询
     *
     * @param req 查询请求
     * @return 查询结果
     */
    default <R> RestResult<R> execute(Query req) {
        return RestResult.ok(AppFrame.handlerBus().execute(req));
    }

    /**
     * 执行命令
     *
     * @param cmd 命令请求
     * @return 命令结果
     */
    default <R> RestResult<R> execute(Command cmd) {
        return RestResult.ok(AppFrame.handlerBus().execute(cmd));
    }
}
