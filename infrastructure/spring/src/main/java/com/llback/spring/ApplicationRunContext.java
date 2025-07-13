package com.llback.spring;

import org.springframework.context.ApplicationContext;

public class ApplicationRunContext {

    /**
     * 上下文
     */
    private final ApplicationContext context;

    public ApplicationRunContext(ApplicationContext context) {
        this.context = context;
    }
}
