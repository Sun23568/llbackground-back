package com.llback.frame;

import com.llback.frame.context.AppRunContext;

public class LLBack implements AppFrame {
    final HandlerBus handlerBus;

    private final BeanContext beanContext;

    // 静态实例
    static LLBack inst;

    private LLBack(AppRunContext runContext) {
        this.handlerBus = new MapHandlerBus();
        this.beanContext = new BeanContext(runContext);

    }

    static LLBack build(AppRunContext runContext) {
        inst = new LLBack(runContext);
        return inst;
    }

    void load() {
        // 加载Handler
        beanContext.scanBean(Handler.class, this.handlerBus::add);
    }

    /**
     * 获取运行时上下文
     */
    public AppRunContext getRunContext() {
        return beanContext.getRunContext();
    }
}
