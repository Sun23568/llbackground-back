package com.llback.frame.dto;

import lombok.Getter;

/**
 * @author hex
 * @date 2024/3/22 15:10
 */
@Getter
public class PageQueryReq<T> implements Query {

    /**
     * 查询参数
     */
    private final T req;

    /**
     * 分页参数
     */
    private final PageQuery page;

    /**
     * 构造方法
     *
     * @param req  查询参数
     * @param page 分页参数
     */
    protected PageQueryReq(T req, PageQuery page) {
        this.req = req;
        this.page = page;
    }

    /**
     * 构造方法
     *
     * @param req       查询参数
     * @param pageIndex 页码（从0开始）
     * @param pageSize  每页大小
     */
    protected PageQueryReq(T req, int pageIndex, int pageSize) {
        this(req, PageQuery.of(pageIndex, pageSize));
    }


}
