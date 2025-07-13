package com.llback.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: llback
 * @description: SpringBoot启动类
 * @date: 2017/10/24 11:55
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,           // 禁用数据源自动配置
        DataSourceTransactionManagerAutoConfiguration.class, // 禁用事务管理器自动配置
        HibernateJpaAutoConfiguration.class          // 禁用JPA/Hibernate自动配置
})
@ComponentScan(basePackages = {"com.llback"})
public class MyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MyApplication.class, args);
        applicationContext.start();
    }
}
