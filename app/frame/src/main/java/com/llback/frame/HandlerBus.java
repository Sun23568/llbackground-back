package com.llback.frame;

public interface HandlerBus {
    /**
     * 注册Handler
     *
     * @param handler
     */
    void add(Handler<?, ?> handler);

    /**
     * 注册Handler
     *
     * @param reqType
     * @param handler
     */
    void add(Class<?> reqType, Handler<?, ?> handler);

    <R, Q> R execute(Q req);
}
