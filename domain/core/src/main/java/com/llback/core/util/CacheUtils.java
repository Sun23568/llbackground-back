package com.llback.core.util;

import com.llback.common.service.CacheService;
import com.llback.common.types.CacheType;
import com.llback.core.user.vo.UserCacheItemVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CacheUtils implements ApplicationContextAware {
    /**
     * 缓存服务
     */
    private static CacheService cacheService;

    /**
     * 获取用户信息
     */
    public static UserCacheItemVo getUser(String userId) {
        return (UserCacheItemVo) cacheService.getCacheObject(CacheType.USER, userId);
    }

    /**
     * 获取用户信息
     */
    public static boolean cacheUser(UserCacheItemVo userCache) {
        if (userCache == null || !userCache.validCache()) {
            return false;
        }
        return cacheService.setCacheObject(CacheType.USER, userCache.getUser().getUserId().toString(), userCache);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CacheUtils.cacheService = applicationContext.getBean(CacheService.class);
    }
}
