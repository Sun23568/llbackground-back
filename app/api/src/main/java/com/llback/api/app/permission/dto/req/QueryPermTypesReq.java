package com.llback.api.app.permission.dto.req;

import com.llback.frame.dto.Query;

/**
 * 查询权限类型列表请求
 */
public class QueryPermTypesReq implements Query {
    /**
     * 空请求单例
     */
    public static final QueryPermTypesReq EMPTY = new QueryPermTypesReq();

    private QueryPermTypesReq() {
    }
}
