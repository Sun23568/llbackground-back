package com.llback.frame.context;

import java.util.Collection;

/**
 * 运行上下文
 */
public interface AppRunContext {
    /**
     * 获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getBean(Class<T> clazz);

    /**
     * 获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Collection<T> getBeansOfType(Class<?> clazz);

    /**
     * 获取当前rest上下文
     */
    RestContext getCurrentRestContext();
}
