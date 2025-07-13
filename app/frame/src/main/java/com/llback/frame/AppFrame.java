package com.llback.frame;


import com.llback.frame.dto.Query;
import com.llback.frame.rest.RestResult;
import org.springframework.context.ApplicationContext;

/**
 * 框架接口
 */
public interface AppFrame {
    /**
     * 加载系统
     *
     * @param runContext 运行上下文
     */
    static void load(ApplicationContext runContext) {
        if (null == LLBack.inst) {
            synchronized (LLBack.class) {
                if (null == LLBack.inst) {
                    LLBack.build(runContext).load();
                }
            }
        }
    }

    static HandlerBus handlerBus(){
        return LLBack.inst.handlerBus;
    }
}
