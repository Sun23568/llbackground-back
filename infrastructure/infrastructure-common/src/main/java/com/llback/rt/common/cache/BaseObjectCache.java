package com.llback.rt.common.cache;

import com.alibaba.fastjson.JSON;
import com.llback.common.service.CacheService;
import com.llback.common.types.CacheType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存对象基类
 */
@Slf4j
@Component
public abstract class BaseObjectCache<T> {
    /**
     * redis工具
     */
    @Autowired
    private RedisCacheUtil redisCacheUtil;

    // 连接符
    private static final String CONNECTOR = ":";

    /**
     * 获取缓存类型
     */
    protected abstract CacheType getCacheType();

    /**
     * 获取缓存前缀
     */
    protected abstract String prefix();

    /**
     * 重新加载缓存
     */
    protected abstract T onReload(String key);

    /**
     * 获取缓存的key
     */
    String getCacheKey(String key) {
        return prefix().concat(CONNECTOR).concat(key);
    }

    /**
     * 获取缓存对象
     */
    public T getCacheObject(String key) {
        // 查询redis
        return (T) redisCacheUtil.get(getCacheKey(key));
    }

    /**
     * 设置缓存对象
     */
    public boolean setCacheObject(String key, T value) {
        redisCacheUtil.set(getCacheKey(key), value);
        return true;
    }

    /**
     * 重新加载缓存
     *
     * @param key
     * @return
     */
    public T reloadCache(String key) {
        // 删除redis缓存
        return onReload(key);
    }
}
