package com.llback.frame;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenConfig {
    @Bean
    public StpLogic getStpLogicJwt() {
        // Sa-Token 整合 jwt (Simple 简单模式)
        return new StpLogicJwtForSimple();
    }
}
