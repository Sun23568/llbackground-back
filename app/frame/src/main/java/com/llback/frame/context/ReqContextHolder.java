package com.llback.frame.context;

import com.alibaba.fastjson.JSON;
import com.llback.common.exception.SysException;
import com.llback.common.types.UserId;
import com.llback.common.util.AssertUtil;
import com.llback.frame.AppFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 上下文处理器
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@Slf4j
public class ReqContextHolder implements ReqContext {
    /**
     * 线程Map存取请求上下文
     */
    static final InheritableThreadLocal<ReqContextHolder> CONTEXT = new InheritableThreadLocal<>();

    /**
     * 当前用户会话信息
     */
    private UserSession userSession;

    /**
     * 当前会话信息
     */
    private SessionMap sessionMap;

    /**
     * 当前请求上下文
     */
    private RestContext restContext;

    /**
     * 会话管理器
     */
    private SessionMgr sessionMgr;

    public ReqContextHolder(RestContext restContext, SessionMgr sessionMgr) {
        this.restContext = restContext;
        this.sessionMgr = sessionMgr;
        // 获取会话信息
        this.sessionMap = restContext.getSessionMap(sessionMgr);
    }

    @Override
    public UserSession getUserSession() {
        return null;
    }

    @Override
    public RestContext getRestContext() {
        return this.restContext;
    }

    @Override
    public SessionMap getSessionMap() {
        return null;
    }

    /**
     * Re
     * 获取当前线程中的 ReqContext，不存在自动创建
     *
     * @return ReqContext
     */
    public static ReqContextHolder getCurrent() {
        ReqContextHolder holder = ReqContextHolder.CONTEXT.get();
        if (null == holder || null == holder.sessionMap) {
            log.info("获取当前线程中的ReqContextHolder为空，进行初始化");
            holder = new ReqContextHolder(AppFrame.currentRestContext(), AppFrame.service(SessionMgr.class));
            ReqContextHolder.CONTEXT.set(holder);
        }
        log.info("获取当前线程中的ReqContextHolder：{}", JSON.toJSONString(holder.sessionMap));
        holder.checkSession();
        return holder;
    }

    @Override
    public UserSession createSession(UserId userId, Map<String, Object> extData) {
        AssertUtil.assertTrue(null != userId && !userId.isNullId(), "用户ID不能为空");
        this.sessionMap = SessionMap.of(userId, extData);
        this.updateSession(sessionMap);
        this.userSession = this.sessionMgr.getUserSession(sessionMap);
        return this.userSession;
    }

    @Override
    public SessionMap createGuestSession(Map<String, Object> extData) {
        return null;
    }

    @Override
    public void updateSession() {

    }

    @Override
    public void updateSession(SessionMap sessionMap) {

    }

    /**
     * 验证当前会话是否有效
     */
    void checkSession() {
        AssertUtil.assertTrue(null == this.userSession || this.isValidSession(), () -> {
            throw new SysException("会话信息无效");
        });
    }

    /**
     * 是否有效Session
     */
    private boolean isValidSession() {
        return null != this.userSession && null != sessionMap && null != this.userSession.getUserId() &&
                this.userSession.getUserId().equals(sessionMap.getUserId());
    }

    /**
     * 移除当前线程中的 ReqContext
     */
    static void removeContext() {
        ReqContextHolder.CONTEXT.remove();
    }
}
