package com.llback.dal.ai.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * AI模块Dao
 *
 * @author yz.sun
 * @date 2025/10/6
 */
@Mapper
public interface AiDao {
    /**
     * 根据AI菜单ID删除背景图片
     *
     * @author yz.sun
     * @date 2025/10/6
     */
    void deleteBackground(String aiMenuId);

    /**
     * 添加背景图片
     *
     * @author yz.sun
     * @date 2025/10/6
     */
    int addBackground(String aiMenuId, String fileId);
}
