package com.llback.spring.filter;

import cn.dev33.satoken.stp.StpUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Feign拦截器，用于在Feign请求中添加token
 */
@Component
public class FeignTokenInterceptor implements RequestInterceptor {
    @Value("${sa-token.token-name}")
    private String tokenName;

    @Override
    public void apply(RequestTemplate template) {
        // 检查用户是否已登录
        if (StpUtil.isLogin()) {
            // 获取当前用户的token
            String token = StpUtil.getTokenValue();
            if (token != null) {
                // 在请求头中添加token
                template.header("Cookie", tokenName + "=" + token);
            }
        }
    }
}
