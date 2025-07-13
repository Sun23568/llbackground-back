package com.llback.frame.dto;

import com.llback.common.util.AssertUtil;
import lombok.Getter;

import java.util.Collection;

/**
 * css @2024
 * 分页结果的实现类
 *
 * @author hex
 * @date 2023/11/15 13:55
 */
@Getter
class PageResultVo<T> implements PageResult<T> {

    /**
     * 当前页结果集
     */
    private final Collection<T> items;

    /**
     * 每页大小
     */
    private final int pageSize;

    /**
     * 当前页在total中的偏移量，从0开始
     */
    private final int pageIndex;

    /**
     * 总记录数
     */
    private final int total;

    /**
     * 构造函数
     *
     * @param items     当前页结果集
     * @param pageSize  每页大小
     * @param pageIndex 当前页在total中的偏移量，从0开始
     * @param total     总记录数
     */
    public PageResultVo(Collection<T> items, int pageSize, int pageIndex, int total) {
        AssertUtil.assertTrue(pageSize > 0, "pageSize must > 0");
        AssertUtil.assertTrue(pageIndex >= 0, "pageIndex must >= 0");
        AssertUtil.assertTrue(total >= 0, "total must >= 0");

        this.items = items;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.total = total;
    }
}
