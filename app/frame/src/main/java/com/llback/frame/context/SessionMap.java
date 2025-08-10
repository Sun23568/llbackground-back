package com.llback.frame.context;

import com.llback.common.types.TokenId;
import com.llback.common.types.UserId;

import java.util.Collections;
import java.util.Map;

/**
 * css-backend @2024
 * 会话信息
 *
 * @author hex
 * @date 2024/2/22 19:15
 */

public interface SessionMap {

    /**
     * 密码盐
     */
    String KEY_SALT = "salt";

    /**
     * token创建时间戳
     */
    String TOKEN_CRT_TIMESTAMP = "Token-Crt-Timestamp";

    /**
     * 游客用户ID
     */
    static final String GUEST_USER_ID = "GUEST";

    /**
     * 获取用户
     */
    UserId getUserId();

    /**
     * 获取令牌
     */
    TokenId getToken();

    /**
     * 设置令牌
     */
    void setToken(TokenId token);

    /**
     * 设置令牌过期时间戳
     *
     * @author yz.sun
     * @date 2024/5/29
     */
    void setTokenExpireTimestamp(long expireTimestamp);

    /**
     * 获取令牌过期时间戳
     *
     * @author yz.sun
     * @date 2024/5/29
     */
    long getTokenExpireTimestamp();

    /**
     * 是否是游客
     */
    default boolean isGuest() {
        return GUEST_USER_ID.equals(getUserId());
    }

    /**
     * 获取扩展数据
     */
    <T> T get(String key, T defVal);

    /**
     * 设置扩展数据
     */
    void put(String key, Object value);

    /**
     * 移除扩展数据
     *
     * @author yz.sun
     * @date 2024/4/27
     */
    void remove(String key);

    /**
     * 扩展数据
     */
    Map<String, Object> getExtData();

    /**
     * 有扩展数据
     */
    static SessionMap of(UserId userId, Map<String, Object> extData) {
        return of(userId, null, extData);
    }

    /**
     * 有扩展数据and token
     */
    static SessionMap of(UserId userId, TokenId token, Map<String, Object> extData) {
        return new SessionMapImpl(userId, token, extData);
    }

    /**
     * 无扩展数据
     */
    static SessionMap of(UserId userId) {
        return of(userId, Collections.emptyMap());
    }

    /**
     * 获取Token创建时间戳
     *
     * @author yz.sun
     * @date 2024/6/4
     */
    default long getTokenCrtTimestamp() {
        return Long.parseLong(this.get(TOKEN_CRT_TIMESTAMP, "0"));
    }

    /**
     * 设置Token创建时间戳
     *
     * @author yz.sun
     * @date 2024/6/4
     */
    default void setTokenCrtTimestamp(long tokenCrtTimestamp) {
        this.put(TOKEN_CRT_TIMESTAMP, String.valueOf(tokenCrtTimestamp));
    }
}
