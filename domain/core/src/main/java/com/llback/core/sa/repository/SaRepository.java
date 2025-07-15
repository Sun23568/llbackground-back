package com.llback.core.sa.repository;

import com.llback.core.sa.eo.UserEo;

/**
 * 鉴权仓储
 */
public interface SaRepository {
    /**
     * 根据用户ID查询用户信息
     *
     * @param userId
     * @return
     */
    UserEo findUser(String userId);
}
