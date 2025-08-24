package com.llback.rt.common.cache;

import com.alibaba.fastjson2.JSONObject;
import com.llback.common.types.CacheType;
import com.llback.common.types.*;
import com.llback.common.util.AssertUtil;
import com.llback.core.menu.eo.MenuEo;
import com.llback.core.menu.repository.MenuRepository;
import com.llback.core.perm.eo.FuncPermEo;
import com.llback.core.perm.repository.PermRepository;
import com.llback.core.user.eo.UserEo;
import com.llback.core.user.repository.UserRepository;
import com.llback.core.user.vo.UserCacheItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户缓存
 */
@Component
public class UserCache extends BaseObjectCache<UserCacheItemVo> {
    /**
     * 用户仓储
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * 权限仓储
     */
    @Autowired
    private PermRepository permRepository;

    /**
     * 菜单仓储
     */
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public CacheType getCacheType() {
        return CacheType.USER;
    }

    @Override
    public String prefix() {
        return "USER";
    }

    /**
     * 重新加载用户
     */
    @Override
    public UserCacheItemVo onReload(String key) {
        AssertUtil.notEmpty(key, "用户ID不能为空");
        UserId userId = UserId.of(key);
        // 获取用户信息
        UserEo userEo = userRepository.findUser(userId);
        userEo.clearPassword();
        // 获取菜单
        Collection<MenuEo> menuList = menuRepository.queryUserMenus(userId);
        Set<String> menus = menuList.stream().map(MenuEo::getMenuCode).map(MenuCode::toString).collect(Collectors.toSet());

        // 获取权限
        List<FuncPermEo> funcPermEos = permRepository.queryUserPerms(UserId.of(userId));
        Set<String> perms = funcPermEos.stream().map(FuncPermEo::getPermCode).map(FuncCode::toString).collect(Collectors.toSet());

        return UserCacheItemVo.builder()
                .user(userEo)
                .menus(menus)
                .perms(perms)
                .crtTimestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 转换对象
     */
    @Override
    protected UserCacheItemVo toObj(JSONObject from) {
        JSONObject user = from.getJSONObject("user");
        UserEo userEo = UserEo.builder()
                .userId(UserId.of(user.getString("userId")))
                .avatar(StringId.of(user.getString("avatar")))
                .userName(UserName.of(user.getString("userName")))
                .build();
        return UserCacheItemVo.builder()
                .user(userEo)
                .menus(from.getObject("menus", Set.class))
                .perms(from.getObject("perms", Set.class))
                .crtTimestamp(from.getLong("crtTimestamp"))
                .build();
    }
}
