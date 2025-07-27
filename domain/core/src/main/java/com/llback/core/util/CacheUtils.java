package com.llback.core.util;

import com.llback.common.service.CacheService;
import com.llback.common.types.CacheType;
import com.llback.core.sa.eo.UserEo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CacheUtils implements ApplicationContextAware {
    /**
     * 缓存服务
     */
    @Autowired
    private static CacheService cacheService;

    /**
     * 获取用户信息
     */
    public static UserEo getUser(String userId) {
        return cacheService.getCacheObject(CacheType.USER, userId);
    }

    /**
     * 获取用户信息
     */
    public static boolean cacheUser(UserEo user) {
        if (user == null || StringUtils.isEmpty(user.getUserId())) {
            return false;
        }
        return cacheService.setCacheObject(CacheType.USER, user.getUserId().toString(), user);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CacheUtils.cacheService = applicationContext.getBean(CacheService.class);
    }
}
