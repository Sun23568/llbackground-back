package com.llback.api.app.sa.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

/**
 * 获取头像请求
 */
@Data
public class GetAvatarReq implements Query {
    /**
     * 头像Id
     */
    private String avatarId;

    private GetAvatarReq(String avatarId) {
        this.avatarId = avatarId;
    }

    public static GetAvatarReq of(String avatarId) {
        return new GetAvatarReq(avatarId);
    }
}
