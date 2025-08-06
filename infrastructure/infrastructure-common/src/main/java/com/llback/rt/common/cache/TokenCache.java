package com.llback.rt.common.cache;

import com.alibaba.fastjson2.JSONObject;
import com.llback.common.types.CacheType;
import com.llback.core.sa.eo.TokenEo;
import org.springframework.stereotype.Component;

/**
 * TOKEN缓存
 */
@Component
public class TokenCache extends BaseObjectCache<TokenEo> {
    @Override
    public com.llback.common.types.CacheType getCacheType() {
        return CacheType.TOKEN;
    }

    @Override
    public String prefix() {
        return "TOKEN";
    }

    /**
     * 重新加载缓存TOKEN
     *
     * @param key
     * @return
     */
    @Override
    protected TokenEo onReload(String key) {
        return null;
    }

    /**
     * 将JSONObject转为对象
     */
    @Override
    protected TokenEo toObj(JSONObject from) {
        return null;
    }
}
