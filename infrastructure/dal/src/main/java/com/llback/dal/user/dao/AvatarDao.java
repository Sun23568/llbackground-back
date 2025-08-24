package com.llback.dal.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 头像仓储
 */
@Mapper
public interface AvatarDao {
    /**
     * 添加头像
     */
    int addAvatar(@Param("avatarId") String avatarId, @Param("avatarBase64") String avatarBase64);

    /**
     * 获取头像
     */
    String getAvatar(String avatarId);
}
