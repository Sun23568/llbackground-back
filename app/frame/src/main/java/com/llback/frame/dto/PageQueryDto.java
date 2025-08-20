package com.llback.frame.dto;

import com.llback.common.util.AssertUtil;
import lombok.Getter;

@Getter
public class PageQueryDto {

    /**
     * 页码（从0开始）
     */
    private final int pageIndex;
    /**
     * 每页大小
     */
    private final int pageSize;


    /**
     * 构造方法
     *
     * @param pageIndex 页码（从0开始）
     * @param pageSize  每页大小
     */
    public PageQueryDto(int pageIndex, int pageSize) {
        AssertUtil.assertTrue(pageIndex >= 0, "pageIndex must be greater than or equal to 0");
        AssertUtil.assertTrue(pageSize > 0 && pageSize < 10000, "pageSize must be greater than 0 and less than 10000");
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
