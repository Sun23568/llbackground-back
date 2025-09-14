package com.llback.common.service;

import com.llback.common.types.CacheType;

public interface CacheService {
    /**
     * 获取缓存字符串
     *
     * @param cacheType
     * @param key
     * @return
     */
    String getCacheStr(CacheType cacheType, String key);

    /**
     * 获取缓存对象
     *
     * @param cacheType
     * @param key
     * @return
     */
    Object getCacheObject(CacheType cacheType, String key);

    /**
     * 设置缓存对象
     *
     * @param cacheType
     * @param key
     * @param value
     * @return
     */
    boolean setCacheObject(CacheType cacheType, String key, Object value);

    Object reloadCache(CacheType cacheType, String string);
}
