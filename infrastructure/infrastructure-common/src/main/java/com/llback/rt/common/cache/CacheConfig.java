package com.llback.rt.common.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class CacheConfig {
    private String host = "127.0.0.1";
    private int port = 6379;
    private int timeout = 2000;
    private String password;
    private int maxTotal = 20;
    private int maxIdle = 10;
    private int minIdle = 5;
}
