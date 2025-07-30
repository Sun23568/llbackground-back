package com.llback.frame.context;

import cn.dev33.satoken.stp.StpUtil;
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
        if (checkToken()){

        }
        return null;
    }

    private boolean checkToken() {
        // token是否有效
        if (!StpUtil.isLogin()){
            StpUtil.login("snutes111t");
            return false;
        }
        Object loginId = StpUtil.getLoginId();
        return true;
    }
}
