package com.llback.frame.context;

/**
 * rest上下文
 */
public interface RestContext {
    /**
     * 获取sessionMap
     *
     * @param sessionMgr
     * @return
     */
    SessionMap getSessionMap(SessionMgr sessionMgr);

    /**
     * 检查登录状态
     *
     * @param autoRenew 自动续期
     */
    void checkLogin(boolean autoRenew);
}
