package com.llback.dal.ai.dao;

import com.llback.dal.ai.po.AiConfigPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 新增AI配置
     *
     * @param aiConfigPo AI配置
     */
    void insertAiConfig(AiConfigPo aiConfigPo);

    /**
     * 更新AI配置
     *
     * @param aiConfigPo AI配置
     */
    void updateAiConfig(AiConfigPo aiConfigPo);

    /**
     * 根据菜单代码查询AI配置
     *
     * @param menuCode 菜单代码
     * @return AI配置
     */
    AiConfigPo queryConfigByMenuCode(@Param("menuCode") String menuCode);

    /**
     * 查询AI菜单配置列表
     *
     * @return AI配置列表
     */
    List<AiConfigPo> queryAllConfig();

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
}
