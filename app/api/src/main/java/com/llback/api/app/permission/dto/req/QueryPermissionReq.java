package com.llback.api.app.permission.dto.req;

import com.llback.frame.dto.PageQuery;
import lombok.Data;

/**
 * 权限分页查询请求
 */
@Data
public class QueryPermissionReq extends PageQuery {
    /**
     * 权限类型（过滤条件）
     */
    private String permType;

    /**
     * 创建查询权限列表请求参数
     *
     * @param pageIndex 页码
     * @param pageSize 每页数量
     */
    public static QueryPermissionReq of(int pageIndex, int pageSize) {
        QueryPermissionReq req = new QueryPermissionReq();
        return (QueryPermissionReq) req.setPageQueryDto(pageIndex, pageSize);
    }
}
