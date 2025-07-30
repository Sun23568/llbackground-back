package com.llback.frame;

import lombok.Getter;

/**
 * 处理器信息
 */
@Getter
public class HandlerInfo {
    /**
     * 请求类类型
     */
    Class<?> reqType;

    /**
     * 处理器
     */
    private Handler<?, ?> handler;

    /**
     * 是否公开
     */
    private boolean pubAcl;

    /**
     * 权限
     */
    private String perm;

    /**
     * 权限列表
     */
    private String[] hasAnyPerm;

    private HandlerInfo() {
    }

    private HandlerInfo(Class<?> reqType, Handler<?, ?> handler) {
        this.reqType = reqType;
        this.handler = handler;
        PubAcl publicAcl = handler.getClass().getAnnotation(PubAcl.class);
        if (publicAcl != null) {
            this.pubAcl = true;
        } else {
            HandlerAcl handlerAcl = handler.getClass().getAnnotation(HandlerAcl.class);
            if (handlerAcl != null) {
                this.perm = handlerAcl.value();
                this.hasAnyPerm = handlerAcl.hasAny();
            }
            this.pubAcl = false;
        }
    }

    public static HandlerInfo of(Class<?> reqType, Handler<?, ?> handler) {
        return new HandlerInfo(reqType, handler);
    }

    public Handler<?, ?> getHandler() {
        return this.handler;
    }
}
