package com.llback.dal.ai.dao;

import com.llback.dal.ai.po.AiConfigPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 查询AI模块菜单背景图片
     *
     * @param aiMenuId
     * @return
     */
    AiConfigPo queryConfig(String aiMenuId);

    /**
     * 查询AI菜单配置列表
     */
    List<AiConfigPo> queryAllConfig();
}
