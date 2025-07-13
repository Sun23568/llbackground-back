package com.llback.frame;

import org.springframework.context.ApplicationContext;

public class LLBack implements AppFrame {

    static ApplicationContext runContext;

    final HandlerBus handlerBus;

    private final BeanContext beanContext;

    // 静态实例
    static LLBack inst;

    private LLBack(ApplicationContext runContext) {
        this.handlerBus = new MapHandlerBus();
        this.beanContext = new BeanContext(runContext);
        this.runContext = runContext;

    }

    static LLBack build(ApplicationContext runContext) {
        inst = new LLBack(runContext);
        return inst;
    }

    void load() {
        // 加载Handler
        beanContext.scanBean(Handler.class, this.handlerBus::add);
    }
}
