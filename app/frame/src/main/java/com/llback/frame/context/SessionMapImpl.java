package com.llback.frame.context;

import com.llback.common.types.TokenId;
import com.llback.common.types.UserId;
import com.llback.common.util.AssertUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * css-backend @2024
 *
 * @author hex  2024/2/22 19:15
 */

@Getter
class SessionMapImpl implements SessionMap {

    /**
     * 用户ID
     */
    private final UserId userId;

    @Setter
    private TokenId token;

    @Setter
    private long tokenExpireTimestamp;

    private final Map<String, Object> extData;


    SessionMapImpl(UserId userId, TokenId token, Map<String, Object> extData) {
        AssertUtil.notNull(userId, "invalid userId");
        this.userId = userId;
        this.token = token;
        this.extData = extData == null ? new HashMap<>() : new HashMap<>(extData);
    }

    @Override
    public boolean isGuest() {
        return this.userId.isGuest();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defVal) {
        return (T) this.extData.getOrDefault(key, defVal);
    }


    @Override
    public void put(String key, Object value) {
        this.extData.put(key, value);
    }

    /**
     * 移除扩展数据
     *
     * @author yz.sun
     * @date 2024/4/27
     */
    @Override
    public void remove(String key) {
        this.extData.remove(key);
    }

    @Override
    public Map<String, Object> getExtData() {
        return Collections.unmodifiableMap(this.extData);
    }

    @Override
    public long getTokenCrtTimestamp() {
        return SessionMap.super.getTokenCrtTimestamp();
    }

    @Override
    public void setTokenCrtTimestamp(long tokenCrtTimestamp) {
        SessionMap.super.setTokenCrtTimestamp(tokenCrtTimestamp);
    }

    @Override
    public String getUserId() {
        return this.userId.toString();
    }

    @Override
    public TokenId getToken() {
        return this.token;
    }

    @Override
    public void setToken(TokenId token) {
        this.token = token;
    }
}
