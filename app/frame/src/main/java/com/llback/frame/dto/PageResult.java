package com.llback.frame.dto;

import java.util.Collection;

/**
 * css @2024
 * 分页结果
 *
 * @author hex
 * @date 2023/11/15 13:55
 */
public interface PageResult<T> {

    /**
     * 当前页记录集
     *
     * @return Collection
     */
    Collection<T> getItems();

    /**
     * 每页记录数
     *
     * @return int
     */
    int getPageSize();

    /**
     * 当前页的页码，从0开始
     */
    int getPageIndex();

    /**
     * 总数
     *
     * @return int
     */
    int getTotal();

    /**
     * 当前页
     *
     * @return int
     */
    default int getPageNo() {
        return this.getPageIndex() + 1;
    }


    /**
     * 总页数
     *
     * @return int
     */
    default int getPageCount() {
        return this.getTotal() / this.getPageSize() + (this.getTotal() % this.getPageSize() > 0 ? 1 : 0);
    }

    /**
     * 构造分页结果
     *
     * @param items     当前页记录集
     * @param pageSize  每页记录数
     * @param pageIndex 当前页，从0开始
     * @param total     总数
     * @return PageResult
     */
    static <T> PageResult<T> ofByPageIndex(Collection<T> items, int pageSize, int pageIndex, int total) {
        return new PageResultVo<>(items, pageSize, pageIndex, total);
    }

    /**
     * 构造分页结果
     *
     * @param items    当前页记录集
     * @param pageSize 每页记录数
     * @param pageNo   当前页，从1开始
     * @param total    总数
     * @return PageResult
     */
    static <T> PageResult<T> of(Collection<T> items, int pageSize, int pageNo, int total) {
        return ofByPageIndex(items, pageSize, pageNo - 1, total);
    }
}
