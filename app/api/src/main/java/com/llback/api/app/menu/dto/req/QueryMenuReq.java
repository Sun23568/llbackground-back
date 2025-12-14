package com.llback.api.app.menu.dto.req;

import com.llback.frame.dto.PageQuery;
import lombok.Data;

/**
 * 菜单分页查询请求
 */
@Data
public class QueryMenuReq extends PageQuery {
    /**
     * 创建查询菜单列表请求参数
     *
     * @param pageIndex 页码
     * @param pageSize 每页数量
     */
    public static QueryMenuReq of(int pageIndex, int pageSize) {
        QueryMenuReq req = new QueryMenuReq();
        return (QueryMenuReq) req.setPageQueryDto(pageIndex, pageSize);
    }
}
