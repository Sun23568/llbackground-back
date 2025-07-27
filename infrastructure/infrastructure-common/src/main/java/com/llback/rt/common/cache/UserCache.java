package com.llback.rt.common.cache;

import com.alibaba.fastjson.JSONObject;
import com.llback.common.types.CacheType;
import com.llback.core.sa.eo.UserEo;
import org.springframework.stereotype.Component;

@Component
public class UserCache extends BaseObjectCache<UserEo> {
    @Override
    public CacheType getCacheType() {
        return CacheType.USER;
    }

    @Override
    public String prefix() {
        return "USER";
    }

    /**
     * 缓存用户
     *
     * @param userEo
     * @return
     */
    @Override
    public String toJSON(UserEo userEo){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userEo.getUserId().toString());
        jsonObject.put("userName", userEo.getUserName().toString());
        return jsonObject.toJSONString();
    }

    /**
     * 解析缓存用户
     *
     * @param json
     * @return
     */
    @Override
    public UserEo toObj(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        return UserEo.of(jsonObject.getString("userId"), jsonObject.getString("userName"));
    }
}
