package com.llback.core.ai.repository;

import com.llback.core.ai.eo.CharacterCardEo;

import java.util.List;

/**
 * 角色卡仓储接口
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
public interface CharacterCardRepository {
    /**
     * 保存角色卡
     *
     * @param characterCardEo 角色卡实体对象
     */
    void save(CharacterCardEo characterCardEo);

    /**
     * 根据ID查询角色卡
     *
     * @param id 角色卡ID
     * @return 角色卡实体对象
     */
    CharacterCardEo findById(Long id);

    /**
     * 根据用户ID查询角色卡列表
     *
     * @param userId 用户ID
     * @return 角色卡列表
     */
    List<CharacterCardEo> findByUserId(Long userId);

    /**
     * 更新角色卡
     *
     * @param characterCardEo 角色卡实体对象
     */
    void update(CharacterCardEo characterCardEo);

    /**
     * 删除角色卡（逻辑删除）
     *
     * @param id 角色卡ID
     */
    void deleteById(Long id);
}
