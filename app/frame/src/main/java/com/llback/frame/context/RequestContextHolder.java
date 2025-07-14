package com.llback.frame.context;

/**
 * 上下文处理器
 *
 * @author yz.sun
 * @date 2025/7/14
 */
public class RequestContextHolder {
    /**
     * 线程Map存取请求上下文
     */
    private static final InheritableThreadLocal<RequestContextHolder> CONTEXT = new InheritableThreadLocal<>();


}
