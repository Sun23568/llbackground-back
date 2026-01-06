package com.llback.dal.ai.dao;

import com.llback.dal.ai.po.CharacterCardPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色卡Dao接口
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Mapper
public interface CharacterCardDao {
    /**
     * 插入角色卡
     *
     * @param characterCardPo 角色卡持久化对象
     */
    void insert(CharacterCardPo characterCardPo);

    /**
     * 根据ID查询角色卡
     *
     * @param id 角色卡ID
     * @return 角色卡持久化对象
     */
    CharacterCardPo selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询角色卡列表
     *
     * @param userId 用户ID
     * @return 角色卡列表
     */
    List<CharacterCardPo> selectByUserId(@Param("userId") Long userId);

    /**
     * 更新角色卡
     *
     * @param characterCardPo 角色卡持久化对象
     */
    void update(CharacterCardPo characterCardPo);

    /**
     * 逻辑删除角色卡
     *
     * @param id 角色卡ID
     */
    void deleteById(@Param("id") Long id);
}
