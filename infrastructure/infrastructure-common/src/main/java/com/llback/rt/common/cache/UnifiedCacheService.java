package com.llback.rt.common.cache;

import com.llback.common.service.CacheService;
import com.llback.common.types.CacheType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一缓存服务
 */
@Slf4j
@Component
public class UnifiedCacheService implements CacheService {
    /**
     * 缓存类型存储
     */
    private final static Map<CacheType, BaseObjectCache> cacheMap = new HashMap<>();

    /**
     * 注册缓存实例
     */
    public static void registerCache(BaseObjectCache cacheInst) {
        CacheType cacheType = CacheType.of(cacheInst.getCacheType());
        if (cacheMap.containsKey(cacheType)) {
            log.warn("缓存类型{}已存在", cacheType);
            return;
        }
        cacheMap.put(cacheType, cacheInst);
    }

    @Override
    public String getCacheStr(CacheType cacheType, String key) {
        return null;
    }

    @Override
    public Object getCacheObject(CacheType cacheType, String key) {
        return getCacheInst(cacheType).getCacheObject(key);
    }

    @Override
    public boolean setCacheObject(CacheType cacheType, String key, Object value) {
        return getCacheInst(cacheType).setCacheObject(key, value);
    }

    @Override
    public Object reloadCache(CacheType cacheType, String key) {
        return getCacheInst(cacheType).reloadCache(key);
    }

    /**
     * 获取缓存实例
     *
     * @param cacheType
     * @return
     */
    private BaseObjectCache getCacheInst(CacheType cacheType) {
        BaseObjectCache cacheInst = cacheMap.get(cacheType);
        if (cacheInst == null) {
            log.warn("缓存类型{}不存在", cacheType);
        }
        return cacheInst;
    }
}
