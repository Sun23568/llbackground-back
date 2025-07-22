package com.llback.frame.context;


/**
 * css-backend @2024
 * 会话管理
 *
 * @author hex
 * @date 2024/3/8 11:25
 */
public interface SessionMgr {
    /**
     * 根据会话令牌ID及当前操作者获取用户会话
     *
     * @param sessionMap 会话数据
     * @return 用户会话
     */
    UserSession getUserSession(SessionMap sessionMap);

    /**
     * 更新会话令牌
     *
     * @author yz.sun
     * @date 2024/5/29
     */
    void updateToken(SessionMap sessionMap);

    /**
     * 移除会话令牌
     *
     * @author yz.sun
     * @date 2024/5/30
     */
    void removeToken(SessionMap sessionMap);
}
