package com.llback.rt.common.cache;

import com.llback.common.types.CacheType;
import org.springframework.stereotype.Component;

@Component
public class UserCache extends BaseObjectCache<String> {
    @Override
    public CacheType getCacheType() {
        return CacheType.USER;
    }

    @Override
    public String prefix() {
        return "USER";
    }

    @Override
    public String getCacheObject(String key) {
        return null;
    }
}
