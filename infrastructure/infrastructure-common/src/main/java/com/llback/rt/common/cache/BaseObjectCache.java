package com.llback.rt.common.cache;

import com.alibaba.fastjson.JSON;
import com.llback.common.service.CacheService;
import com.llback.common.types.CacheType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存对象基类
 */
@Slf4j
@Primary
@Service
public class BaseObjectCache<T> implements CacheService {
    /**
     * 缓存类型存储
     */
    private final static Map<CacheType, BaseObjectCache> cacheMap = new HashMap<>();

    // 连接符
    private static final String CONNECTOR = ":";

    /**
     * 缓存模板
     */
    @Autowired
    RedisTemplate<String, Object> redisTemplate;


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
        String cacheKey = getCacheKey(key);
        return "test";
    }

    /**
     * 获取缓存对象
     *
     * @param <T>
     * @param cacheType
     * @param key
     * @return
     */
    @Override
    public <T> T getCacheObject(CacheType cacheType, String key) {
        // 获取实例
        BaseObjectCache cacheInst = getCacheInst(cacheType);
        return (T) cacheInst.getCacheObject(key);
    }

    /**
     * 获取缓存对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getCacheObject(String key) {
        // 查询redis
        String json = (String) redisTemplate.opsForValue().get(getCacheKey(key));
        return (T) toObj(json);
    }

    /**
     * 获取缓存实例
     *
     * @param cacheType
     * @return
     */
    private BaseObjectCache getCacheInst(CacheType cacheType) {
        if (!cacheMap.containsKey(cacheType)) {
            log.warn("缓存类型{}不存在", cacheType);
            return null;
        }
        return cacheMap.get(cacheType);
    }

    /**
     * 设置缓存对象
     *
     * @param cacheType
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean setCacheObject(CacheType cacheType, String key, Object value) {
        BaseObjectCache cacheInst = getCacheInst(cacheType);
        return cacheInst.setCacheObject(key, value);
    }

    /**
     * 设置缓存对象
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setCacheObject(String key, T value) {
        String json = toJSON(value);
        String cacheKey = getCacheKey(key);
        redisTemplate.opsForValue().set(cacheKey, json);
        return true;
    }

    /**
     * 转为json
     *
     * @param obj
     * @return
     */
    public String toJSON(T obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 转为对象
     *
     * @param json
     * @return
     */
    public T toObj(String json) {
        return (T) JSON.parseObject(json);
    }
}
