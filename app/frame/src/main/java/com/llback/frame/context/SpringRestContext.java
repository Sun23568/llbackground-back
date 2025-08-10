package com.llback.frame.context;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.jwt.SaJwtUtil;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.hutool.json.JSONObject;
import com.llback.common.exception.NotLoginException;
import com.llback.common.types.TokenId;
import com.llback.common.types.UserId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring rest上下文
 */
public class SpringRestContext implements RestContext {
    /**
     * 请求
     */
    private final HttpServletRequest request;

    /**
     * 响应
     */
    private final HttpServletResponse response;

    public SpringRestContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 获取sessionMap
     *
     * @param sessionMgr
     * @return
     */
    @Override
    public SessionMap getSessionMap(SessionMgr sessionMgr) {
        if (!checkToken()) {
            return null;
        }
        String loginId = StpUtil.getLoginIdAsString();
        String tokenValue = StpUtil.getTokenValue();
        JSONObject extMap = SaJwtUtil.getPayloadsNotCheck(tokenValue, StpUtil.getLoginType(), SaManager.getConfig().getJwtSecretKey());
        SessionMap sessionMap = SessionMap.of(UserId.of(loginId), TokenId.of(tokenValue), extMap);
        // Token失效时间小于会话时长 1/4 , 就自动续期
        if (!UserId.isGuest(loginId) && StpUtil.getTokenTimeout() <= SaManager.getConfig().getTimeout() / 4) {
            createSession(sessionMap);
        }
        return sessionMap;
    }

    @Override
    public void checkLogin(boolean autoRenew) {
        try {
            StpUtil.checkLogin();
        } catch (cn.dev33.satoken.exception.NotLoginException ignored) {
            throw new NotLoginException();
        }
    }

    private boolean checkToken() {
        return StpUtil.isLogin();
    }


    /**
     * 创建session\
     */
    public void createSession(SessionMap sessionMap) {
        // 生成token
        SaLoginParameter saLoginParameter = SaLoginConfig.setExtraData(sessionMap.getExtData()).setTimeout(SaManager.getConfig().getTimeout());
        StpUtil.login(sessionMap.getUserId(), saLoginParameter);
        String tokenValue = StpUtil.getTokenValue();
        sessionMap.setToken(TokenId.of(tokenValue));
        sessionMap.setTokenExpireTimestamp(StpUtil.getTokenInfo().getTokenTimeout() + System.currentTimeMillis() / 1000);
    }
}
