package com.llback.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.llback.frame.AppFrame;

/**
 * Spring配置
 *
 * @author yz.sun
 */
@Configuration
public class SpringAppConfig {
    /**
     * 启动后初始化应用上下文
     */
    @Component
    static class AppContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

        /**
         * 启动后初始化应用上下文
         */
        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            AppFrame.load(event.getApplicationContext());
        }
    }
}