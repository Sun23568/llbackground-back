package com.llback.frame;

import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.function.Consumer;

class BeanContext {

    private final ApplicationContext runContext;

    public BeanContext(ApplicationContext runContext) {
        this.runContext = runContext;
    }

    public <T> T getBean(Class<T> clazz) {
        return runContext.getBean(clazz);
    }

    public <T> void scanBean(Class<T> clazz, Consumer<T>  consumer){
        Map<String, T> beansOfType = runContext.getBeansOfType(clazz);
        beansOfType.values().forEach(consumer);
    }
}
