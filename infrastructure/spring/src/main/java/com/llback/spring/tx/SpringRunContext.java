package com.llback.spring.tx;

import com.llback.frame.context.AppRunContext;
import com.llback.frame.context.RestContext;
import com.llback.frame.context.SpringRestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Slf4j
class SpringRunContext implements AppRunContext {
    /**
     * spring容器
     */
    private final ApplicationContext applicationContext;

    /**
     * rest上下文key
     */
    private static final String ATTR_REST_CONTEXT_KEY = "llback_rest_ctx";

    public SpringRunContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T getBean(Class<T> clazz) {
        Map<String, T> beansOfType = applicationContext.getBeansOfType(clazz);
        return beansOfType.values().stream().findFirst().orElse(null);
    }

    /**
     * 获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> Collection<T> getBeansOfType(Class<?> clazz) {
        Collection<T> collection = (Collection<T>) Collections.unmodifiableCollection(applicationContext.getBeansOfType(clazz).values());
        return collection;
    }

    /**
     * 获取当前rest上下文
     */
    @Override
    public RestContext getCurrentRestContext() {
        ServletRequestAttributes reqAttrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = reqAttrs.getRequest();
        SpringRestContext springRestContext = (SpringRestContext) request.getAttribute(ATTR_REST_CONTEXT_KEY);
        if (springRestContext != null) {
            log.info("未获取到restContext");
            HttpServletResponse response = reqAttrs.getResponse();
            springRestContext = new SpringRestContext(request, response);
            request.setAttribute(ATTR_REST_CONTEXT_KEY, springRestContext);
        }
        log.info("获取到的restContext: {}", springRestContext);
        return springRestContext;
    }
}
