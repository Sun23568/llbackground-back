package com.llback.dal.game.dao;

import com.llback.dal.game.po.GameLevelPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 游戏关卡DAO。
 */
@Mapper
public interface GameLevelDao {
    /**
     * 新增关卡。
     */
    void insert(GameLevelPo gameLevelPo);

    /**
     * 更新关卡。
     */
    void update(GameLevelPo gameLevelPo);

    /**
     * 删除关卡。
     */
    void deleteById(@Param("pkId") String pkId);

    /**
     * 根据ID查询关卡。
     */
    GameLevelPo selectById(@Param("pkId") String pkId);

    /**
     * 查询全部关卡。
     */
    List<GameLevelPo> selectAll();

    /**
     * 查询已发布关卡。
     */
    List<GameLevelPo> selectEnabled();
}
