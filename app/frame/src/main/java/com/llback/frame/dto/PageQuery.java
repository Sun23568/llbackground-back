package com.llback.frame.dto;

public interface PageQuery extends Query {

    /**
     * 每页记录数
     *
     * @return int
     */
    int getPageSize();

    /**
     * 当前页，从0开始
     */
    int getPageIndex();

    /**
     * 当前页，从1开始
     */
    default int pageNo() {
        return this.getPageIndex() + 1;
    }


    /**
     * 构建分页查询条件
     *
     * @param pageIndex 页码（从0开始）
     * @param pageSize  每页大小
     * @return PageQueryVo
     */
    static PageQuery of(int pageIndex, int pageSize) {
        return new PageQueryDto(pageIndex, pageSize);
    }
}
