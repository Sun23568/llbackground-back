package com.llback.frame.context;

import com.llback.common.types.UserId;
import com.llback.common.types.UserName;
import com.llback.common.types.OperationId;

import java.util.Collections;
import java.util.Set;

/**
 * css @2024
 * 客户端会话
 *
 * @author hex
 * @date 2023/11/22 14:06
 */
public interface UserSession {

    /**
     * 获取当前操作者身份ID信息
     *
     * @return OperationId
     */
    OperationId getOperationId();


    /**
     * 获取当前登录的用户账号
     *
     * @return 用户账号
     */
    default UserId getUserId() {
        return this.getOperationId().getUserId();
    }

    /**
     * 获取当前登录的用户名称
     *
     * @return UserName
     */
    default UserName getUserName() {
        return this.getOperationId().getUserName();
    }

    /**
     * 是否拥有指定权限
     *
     * @param permission 权限
     * @return 是否拥有指定权限
     */
    default boolean hasPerm(String permission) {
        return this.getPerms().contains(permission);
    }

    /**
     * 是否拥有任意一个权限
     *
     * @param perms 权限
     * @return 是否拥有任意一个权限
     */
    default boolean hasAnyPerm(Set<String> perms) {
        return perms.stream().anyMatch(this::hasPerm);
    }


    /**
     * 获取当前会话数据
     *
     * @return 会话数据
     */
    SessionMap getSessionMap();

    /**
     * 获取登录次数
     *
     * @author yz.sun
     * @date 2024/4/18
     */
    default int getLoginCount() {
        return 0;
    }

    /**
     * 获取上次登录间隔
     *
     * @author yz.sun
     * @date 2024/4/18
     */
    default int getLastLoginInterval() {
        return 0;
    }

    /**
     * 获取当前用户的菜单列表JSONArray
     *
     * @author yz.sun
     * @date 2024/4/24
     */
    default String getMenuListJson() {
        return null;
    }

    /**
     * 获取当前用户的请求权限列表
     *
     * @author yz.sun
     * @date 2024/5/8
     */
    default Set<String> getPerms() {
        return Collections.emptySet();
    }
}
