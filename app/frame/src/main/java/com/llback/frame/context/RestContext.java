package com.llback.frame.context;

/**
 * rest上下文
 */
public interface RestContext {
    /**
     * 获取sessionMap
     */
    SessionMap getSessionMap(SessionMgr sessionMgr);

    /**
     * 检查登录状态
     */
    void checkLogin(boolean autoRenew);

    /**
     * 创建session
     */
    void createSession(SessionMap sessionMap);
}
