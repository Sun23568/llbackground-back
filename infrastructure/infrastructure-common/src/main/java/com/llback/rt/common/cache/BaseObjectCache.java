package com.llback.rt.common.cache;

import com.alibaba.fastjson2.JSONObject;
import com.llback.common.types.CacheType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        Object obj = redisCacheUtil.get(getCacheKey(key));
        if (obj == null) {
            return reloadCache(key);
        }
        T result = toObj(JSONObject.from(obj));
        return result;
    }

    /**
     * 将json对象转为对象
     */
    protected abstract T toObj(JSONObject from);

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
        T obj = onReload(key);
        redisCacheUtil.delete(getCacheKey(key));
        redisCacheUtil.set(getCacheKey(key), obj, 1800);
        return obj;
    }
}
