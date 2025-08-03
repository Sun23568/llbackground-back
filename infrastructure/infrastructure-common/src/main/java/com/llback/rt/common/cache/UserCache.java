package com.llback.rt.common.cache;

import com.alibaba.fastjson.JSONObject;
import com.llback.common.types.CacheType;
import com.llback.core.user.eo.UserEo;
import com.llback.core.user.vo.UserCacheItemVo;
import org.springframework.stereotype.Component;

@Component
public class UserCache extends BaseObjectCache<UserCacheItemVo> {
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
     * @param userCacheItemVo
     * @return
     */
    @Override
    public String toJSON(UserCacheItemVo userCacheItemVo){
        return JSONObject.toJSONString(userCacheItemVo);
    }

    /**
     * 解析缓存用户
     *
     * @param json
     * @return
     */
    @Override
    public UserCacheItemVo toObj(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        return null;
//        return UserEo.of(jsonObject.getString("userId"), jsonObject.getString("userName"));
    }
}
