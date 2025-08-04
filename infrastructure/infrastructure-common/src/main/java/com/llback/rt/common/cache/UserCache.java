package com.llback.rt.common.cache;

import com.llback.common.types.CacheType;
import com.llback.core.user.vo.UserCacheItemVo;
import org.springframework.stereotype.Component;

/**
 * 用户缓存
 */
@Component
public class UserCache extends BaseObjectCache<UserCacheItemVo> {
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
     *
     * @param userId
     * @return
     */
    @Override
    public UserCacheItemVo onReload(String userId) {
        return null;
    }
}
