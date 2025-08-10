package com.llback.frame;


import com.llback.frame.context.AppRunContext;
import com.llback.frame.context.RestContext;

/**
 * 框架接口
 */
public interface AppFrame {
    /**
     * 加载系统
     *
     * @param runContext 运行上下文
     */
    static void load(AppRunContext runContext) {
        if (null == LLBack.inst) {
            synchronized (LLBack.class) {
                if (null == LLBack.inst) {
                    LLBack.build(runContext).load();
                }
            }
        }
    }

    static HandlerBus handlerBus() {
        return LLBack.inst.handlerBus;
    }

    /**
     * 获取当前请求上下文
     */
    static AppRunContext runContext() {
        return LLBack.inst.getRunContext();
    }

    /**
     * 获取当前请求上下文
     */
    static RestContext currentRestContext() {
        return runContext().getCurrentRestContext();
    }

    static <T> T service(Class<T> clazz) {
        return runContext().getBean(clazz);
    }
}
