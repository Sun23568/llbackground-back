package com.llback.common.service;

import com.llback.common.types.CacheType;

public interface CacheService<T> {

    /**
     * 获取缓存类型
     *
     * @return
     */
    default CacheType getCacheType() {
        return null;
    }

    /**
     * 缓存前缀
     */
    default String prefix(){return null;};

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
    T getCacheObject(CacheType cacheType, String key);
}
