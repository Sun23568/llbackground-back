package com.llback.core.sa.eo;

import com.llback.common.types.TokenId;
import com.llback.common.types.UserId;
import lombok.Getter;

/**
 * TOKEN EO
 */
@Getter
public class TokenEo {
    /**
     * TOKEN
     */
    private TokenId token;

    /**
     * 过期时间
     */
    private long expireTime;

    /**
     * 用户ID
     */
    private UserId userId;

    public TokenEo(String token, long expireTime, String userId) {
        this.token = TokenId.of(token);
        this.expireTime = expireTime;
        this.userId = UserId.of(userId);
    }
}
