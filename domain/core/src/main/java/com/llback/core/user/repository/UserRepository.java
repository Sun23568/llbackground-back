package com.llback.core.user.repository;

import com.github.pagehelper.PageInfo;
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

    /**
     * 查询用户列表
     */
    PageInfo<UserEo> findUsers(int pageIndex, int pageSize);

    /**
     * 添加用户
     */
    int addUser(UserEo userEo);

    /**
     * 修改用户
     */
    int updateUser(UserEo userEo);
}
