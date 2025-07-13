package com.llback.frame;

/**
 * 处理器信息
 */
public class HandlerInfo {
    /**
     * 请求类类型
     */
    Class<?> reqType;

    private Handler<?, ?> handler;

    private HandlerInfo() {
    }

    public HandlerInfo(Class<?> reqType, Handler<?, ?> handler) {
        this.reqType = reqType;
        this.handler = handler;
    }

    public static HandlerInfo of(Class<?> reqType, Handler<?, ?> handler) {
        return new HandlerInfo(reqType, handler);
    }

    public Handler<?,?> getHandler() {
        return this.handler;
    }
}
