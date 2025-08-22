package com.llback.api.app.article.dto.req;

import com.llback.frame.dto.PageQuery;
import lombok.Data;

@Data
public class QueryArticleReq extends PageQuery {
    /**
     * 创建查询用户列表请求参数
     *
     * @param pageIndex
     * @param pageSize
     */
    public static QueryArticleReq of(int pageIndex, int pageSize) {
        QueryArticleReq req = new QueryArticleReq();
        return (QueryArticleReq) req.setPageQueryDto(pageIndex, pageSize);
    }
}
