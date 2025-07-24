package com.llback.rt.common.cache;

import com.llback.common.service.CacheService;
import com.llback.common.types.CacheType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存对象基类
 */
@Slf4j
@Primary
@Service
public abstract class BaseObjectCache<T> implements CacheService {
    /**
     * 缓存类型存储
     */
    private final static Map<CacheType, BaseObjectCache> cacheMap = new HashMap<>();

    // 连接符
    private static final String CONNECTOR = ":";

    @Autowired
    private RedisPoolManager redisPoolManager;


    public static void registerCache(BaseObjectCache cacheInst) {
        CacheType cacheType = cacheInst.getCacheType();
        if (cacheMap.containsKey(cacheType)) {
            log.warn("缓存类型{}已存在", cacheType);
            return;
        }
        cacheMap.put(cacheInst.getCacheType(), cacheInst);
    }

    /**
     * 获取缓存的key
     *
     * @param key
     * @return
     */
    String getCacheKey(String key) {
        return prefix().concat(CONNECTOR).concat(key);
    }

    /**
     * 获取缓存
     *
     * @return
     */
    @Override
    public String getCacheStr(CacheType cacheType, String key) {
        if (!cacheMap.containsKey(cacheType)) {
            log.warn("缓存类型{}不存在", cacheType);
            return null;
        }
        String cacheKey = getCacheKey(key);
        return "test";
    }

    /**
     * 获取缓存对象
     *
     * @param key
     * @return
     */
    public abstract T getCacheObject(String key);

    @Override
    public T getCacheObject(CacheType cacheType, String key){
        System.out.println();
        return (T) cacheMap.get(cacheType).getCacheObject(key);
    }
}
