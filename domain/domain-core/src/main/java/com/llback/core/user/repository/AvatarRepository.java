package com.llback.core.user.repository;

import com.llback.common.types.StringId;

/**
 * 头像仓储
 */
public interface AvatarRepository {
    /**
     * 添加头像
     */
    StringId addAvatar(String avatar);

    /**
     * 获取头像
     */
    String getAvatar(StringId avatarId);
}
