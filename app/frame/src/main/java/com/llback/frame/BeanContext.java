package com.llback.frame;

import com.llback.frame.context.AppRunContext;

import java.util.Collection;
import java.util.function.Consumer;

class BeanContext {

    private final AppRunContext runContext;

    public BeanContext(AppRunContext runContext) {
        this.runContext = runContext;
    }

    public <T> T getBean(Class<T> clazz) {
        return runContext.getBean(clazz);
    }

    /**
     * 扫描指定类型的bean
     *
     * @param clazz
     * @param consumer
     * @param <T>
     */
    public <T> void scanBean(Class<T> clazz, Consumer<T> consumer) {
        Collection<T> beansOfType = runContext.getBeansOfType(clazz);
        beansOfType.forEach(consumer);
    }

    /**
     * 获取运行时上下文
     */
    AppRunContext getRunContext() {
        return runContext;
    }
}