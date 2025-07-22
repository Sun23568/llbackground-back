package com.llback.frame.context;

import com.alibaba.fastjson.JSON;
import com.llback.frame.AppFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * 上下文处理器
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@Slf4j
public class ReqContextHolder {
    /**
     * 线程Map存取请求上下文
     */
    private static final InheritableThreadLocal<ReqContextHolder> CONTEXT = new InheritableThreadLocal<>();

    /**
     * 当前用户会话信息
     */
    private UserSession userSession;

    /**
     * 当前会话信息
     */
    private SessionMap sessionMap;

    public ReqContextHolder(RestContext restContext, SessionMgr sessionMgr) {

    }

    /**
     * Re
     * 获取当前线程中的 ReqContext，不存在自动创建
     *
     * @return ReqContext
     */
    static ReqContextHolder getCurrent() {
        ReqContextHolder holder = ReqContextHolder.CONTEXT.get();
        if (null == holder || null==holder.sessionMap) {
            log.info("获取当前线程中的ReqContextHolder为空，进行初始化");
            holder = new ReqContextHolder(AppFrame.currentRestContext(), AppFrame.service(SessionMgr.class));
            ReqContextHolder.CONTEXT.set(holder);
        }
        log.info("获取当前线程中的ReqContextHolder：{}", JSON.toJSONString(holder.sessionMap));
//        holder.checkSession();
        return holder;
    }
}
