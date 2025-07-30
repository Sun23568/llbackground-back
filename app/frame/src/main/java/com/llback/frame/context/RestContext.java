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
}
