package com.llback.base.user.vo;

import com.llback.base.user.eo.UserEo;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/**
 * @author hex  2024/3/25 14:31
 */
@Getter
@Builder
public class UserCacheItemVo {

    /**
     * 用户信息
     */
    private final UserEo user;

    /**
     * 菜单列表
     */
    private Set<String> menus;

    /**
     * 权限列表
     */
    private final Set<String> perms;

    /**
     * 创建时间
     */
    private final long crtTimestamp;

    public boolean validCache() {
        return user != null && menus != null && perms != null;
    }
}
