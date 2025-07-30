package com.llback.frame.context;

import com.llback.common.types.UserId;

import java.util.HashMap;
import java.util.Map;

/**
 * css @2024
 * 当前请求上下文
 *
 * @author hex
 * @date 2023/11/22 17:33
 */
public interface ReqContext extends AutoCloseable {
    /**
     * 保存签名戳的KEY
     */
    String KEY_SIGN_STAMP = "CSS-SIGN-STAMP";

    /**
     * 浏览器UA
     */
    String USER_AGENT = "User-Agent";

    /**
     * 当前客户端会话
     *
     * @return UserSession
     */
    UserSession getUserSession();

    /**
     * 当前Rest请求上下文
     *
     * @return RestContext
     */
    RestContext getRestContext();

    /**
     * 当前会话
     *
     * @return SessionMap
     */
    SessionMap getSessionMap();

    /**
     * 获取当前请求上下文
     *
     * @return ReqContext
     */
    static ReqContext getCurrent() {
        return ReqContextHolder.getCurrent();
    }

    /**
     * 获取当前用户会话
     *
     * @return UserSession
     */
    static UserSession userSession() {
        return getCurrent().getUserSession();
    }

    /**
     * 获取当前会话
     *
     * @return SessionMap
     */
    static SessionMap sessionMap() {
        return getCurrent().getSessionMap();
    }

    /**
     * 关闭当前请求上下文
     */
    static void closeCurrent() {
        ReqContextHolder holder = ReqContextHolder.CONTEXT.get();
        if (holder != null) {
            holder.close();
        }
    }

    /**
     * 清除当前会话
     */
    static void clearSession() {
        ReqContextHolder holder = ReqContextHolder.CONTEXT.get();
        if (holder != null) {
//            holder.removeSession();
        }
    }

    /**
     * 创建用户会话
     *
     * @param userId  操作UserId
     * @param extData 扩展数据
     */
    UserSession createSession(UserId userId, Map<String, Object> extData);

    /**
     * 创建用户会话
     *
     * @param userId 操作UserId
     */
    default UserSession createSession(UserId userId) {
        return createSession(userId, null);
    }

    /**
     * 创建访客会话
     *
     * @param extData 扩展数据
     */
    SessionMap createGuestSession(Map<String, Object> extData);

    /**
     * 创建访客会话
     */
    default SessionMap createGuestSession() {
        return createGuestSession(new HashMap<>());
    }

    /**
     * 更新会话信息
     */
    void updateSession();

    /**
     * 更新会话
     *
     * @param sessionMap 用户会话
     */
    void updateSession(SessionMap sessionMap);

    /**
     * 自动移除当前请求上下文
     */
    @Override
    default void close() {
        ReqContextHolder.removeContext();
    }
}
