package com.llback.rt.common.cache;

import com.alibaba.fastjson.JSONObject;
import com.llback.common.types.CacheType;
import com.llback.core.sa.eo.TokenEo;
import org.springframework.stereotype.Component;

//@Component
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
     * 缓存TOKEN
     *
     * @param tokenEo
     * @return
     */
    @Override
    public String toJSON(TokenEo tokenEo){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", tokenEo.getToken().toString());
        jsonObject.put("expireTime", tokenEo.getExpireTime());
        jsonObject.put("userId", tokenEo.getUserId().toString());
        return jsonObject.toJSONString();
    }

    /**
     * 解析缓存用户
     *
     * @param json
     * @return
     */
    @Override
    public TokenEo toObj(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        return new TokenEo(jsonObject.getString("token"), jsonObject.getLong("expireTime"), jsonObject.getString("userId"));
    }
}
