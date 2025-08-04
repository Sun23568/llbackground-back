package com.llback.api.app;

import com.alibaba.fastjson.JSONObject;
import com.llback.api.dto.sa.req.GetSessionReq;
import com.llback.api.dto.sa.resp.UserSessionResp;
import com.llback.common.types.EncryptedPassword;
import com.llback.common.types.UserId;
import com.llback.common.types.UserName;
import com.llback.core.user.eo.UserEo;
import com.llback.core.user.vo.UserCacheItemVo;
import com.llback.core.util.CacheUtils;
import com.llback.frame.Handler;
import com.llback.frame.PubAcl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 获取用户会话信息
 */
@PubAcl
@Component
public class GetSessionHandler implements Handler<UserSessionResp, GetSessionReq> {
    @Override
    public UserSessionResp execute(GetSessionReq req) {
//        UserCacheItemVo user = CacheUtils.getUser(UserId.GUEST_UID);
        Set<String> perms = new HashSet<String>(){{
            add("*");
            add("sa:*");
        }};
        UserCacheItemVo build = UserCacheItemVo.builder()
                .user(UserEo.builder().userId(UserId.GUEST_UID).userName(UserName.of("guest")).password(EncryptedPassword.of("asd")).build())
                .perms(perms)
                .menuList(Arrays.asList())
                .crtTimestamp(System.currentTimeMillis())
                .build();

        String jsonString = JSONObject.toJSONString(build);
//        CacheUtils.cacheUser(build);
        return null;
    }
}
