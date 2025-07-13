package com.llback.frame.dto;

/**
 * css @2024
 * 查询请求
 *
 * @author hex
 * @date 2023/11/14 20:10
 */
public interface Query {

    /**
     * 查询id
     *
     * @return id
     */
    default String queryId() {
        return this.getClass().getName();
    }
}
