package com.llback.api.app.user.dto.req;

import com.llback.frame.dto.PageQuery;

/**
 * 查询用户列表请求参数
 */
public class QueryUserListReq extends PageQuery {
    /**
     * 创建查询用户列表请求参数
     *
     * @param pageIndex
     * @param pageSize
     */
    public static QueryUserListReq of(int pageIndex, int pageSize) {
        QueryUserListReq req = new QueryUserListReq();
        return (QueryUserListReq) req.setPageQueryDto(pageIndex, pageSize);
    }
}
