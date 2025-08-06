package com.llback.core.user.repository;

import com.llback.common.types.UserId;
import com.llback.core.user.eo.UserEo;

/**
 * 鉴权仓储
 */
public interface UserRepository {
    /**
     * 根据用户ID查询用户基础信息
     */
    UserEo findUser(UserId userId);
}
