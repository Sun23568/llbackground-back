package com.llback.rt.common.cache;

import com.alibaba.fastjson2.JSONObject;
import com.llback.common.types.CacheType;
import com.llback.common.types.UserId;
import com.llback.common.types.UserName;
import com.llback.common.util.AssertUtil;
import com.llback.core.menu.eo.MenuEo;
import com.llback.core.user.eo.FuncPermEo;
import com.llback.core.user.repository.PermRepository;
import com.llback.core.user.repository.UserRepository;
import com.llback.core.user.eo.UserEo;
import com.llback.core.user.vo.UserCacheItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        Collection<MenuEo> menuList = new ArrayList<>();
        // 获取权限
        List<FuncPermEo> funcPermEos = permRepository.queryUserPerms(UserId.of(userId));
        Set<String> perms = funcPermEos.stream().map(FuncPermEo::getPermissionCode).collect(Collectors.toSet());

        return UserCacheItemVo.builder()
                .user(userEo)
                .menuList(menuList)
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
                .userName(UserName.of(user.getString("userName")))
                .build();
        return UserCacheItemVo.builder()
                .user(userEo)
                .menuList(from.getObject("menuList", Collection.class))
                .perms(from.getObject("perms", Set.class))
                .crtTimestamp(from.getLong("crtTimestamp"))
                .build();
    }
}
