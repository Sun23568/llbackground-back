package com.llback.rt.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Redis健康检查
 */
@Slf4j
@Component
public class RedisHealthCheck {
    /**
     * Redis操作封装类
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(fixedDelay = 1000 * 30)
    public void check() {
        try {
            redisTemplate.opsForValue().get("HEALTH_CHECK");
        } catch (Exception e) {
            log.error("redis异常", e);
            reConnectRedis();
        }

    }

    private void reConnectRedis() {
        try {
            LettuceConnectionFactory connectionFactory =
                    (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
            if (connectionFactory != null) {
                connectionFactory.resetConnection();
                log.info("重置连接池成功");
            }
        } catch (Exception ex) {
            log.error("重置连接池失败", ex);
        }
    }
}
