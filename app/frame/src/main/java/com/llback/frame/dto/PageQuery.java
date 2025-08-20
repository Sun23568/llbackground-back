package com.llback.frame.dto;

/**
 * 分页查询参数
 */
public abstract class PageQuery implements Query {
    /**
     * 分页信息
     */
    public PageQueryDto pageQueryDto;

    /**
     * 每页记录数
     *
     * @return int
     */
    public int getPageSize(){
        return this.pageQueryDto.getPageSize();
    }

    /**
     * 当前页，从0开始
     */
    public int getPageIndex(){
        return this.pageQueryDto.getPageIndex();
    }

    /**
     * 当前页，从1开始
     */
    public int pageNo() {
        return this.getPageIndex() + 1;
    }

    public PageQuery setPageQueryDto(int pageIndex, int pageSize) {
        this.pageQueryDto = new PageQueryDto(pageIndex, pageSize);
        return this;
    }
}
