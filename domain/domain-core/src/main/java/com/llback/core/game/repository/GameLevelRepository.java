package com.llback.core.game.repository;

import com.github.pagehelper.PageInfo;
import com.llback.common.types.StringId;
import com.llback.core.game.eo.GameLevelEo;

import java.util.List;

/**
 * 游戏关卡仓储接口。
 */
public interface GameLevelRepository {
    /**
     * 保存关卡。
     */
    void save(GameLevelEo levelEo);

    /**
     * 更新关卡。
     */
    void update(GameLevelEo levelEo);

    /**
     * 删除关卡。
     */
    void deleteById(StringId pkId);

    /**
     * 根据ID查询关卡。
     */
    GameLevelEo findById(StringId pkId);

    /**
     * 分页查询全部关卡。
     */
    PageInfo<GameLevelEo> findAll(int pageNum, int pageSize);

    /**
     * 查询已发布关卡。
     */
    List<GameLevelEo> findEnabled();
}
