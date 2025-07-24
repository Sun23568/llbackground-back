package com.llback.rt.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class RedisPoolManager {

    /**
     * redis配置
     */
    @Autowired
    private final CacheConfig redisProperties = new CacheConfig();

    /**
     * jedis连接池
     */
    private JedisPool jedisPool;

    /**
     * 初始化Redis连接池
     */
    @PostConstruct
    public void init() {
        log.info("开始初始化Redis连接池...");

        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(redisProperties.getMaxTotal());
            poolConfig.setMaxIdle(redisProperties.getMaxIdle());
            poolConfig.setMinIdle(redisProperties.getMinIdle());
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);

            String password = redisProperties.getPassword();
            if (password != null && !password.isEmpty()) {
                jedisPool = new JedisPool(poolConfig,
                        redisProperties.getHost(),
                        redisProperties.getPort(),
                        redisProperties.getTimeout(),
                        password);
            } else {
                jedisPool = new JedisPool(poolConfig,
                        redisProperties.getHost(),
                        redisProperties.getPort(),
                        redisProperties.getTimeout(),
                        null);
            }

            // 测试连接
            testConnection();

            log.info("Redis连接池初始化完成: {}:{}, MaxTotal: {}, MaxIdle: {}",
                    redisProperties.getHost(),
                    redisProperties.getPort(),
                    poolConfig.getMaxTotal(),
                    poolConfig.getMaxIdle());

        } catch (Exception e) {
            log.error("Redis连接池初始化失败: {}", e.getMessage(), e);
            throw new RuntimeException("Redis连接初始化失败", e);
        }
    }

    /**
     * 测试Redis连接
     */
    private void testConnection() {
        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.ping();
            if ("PONG".equalsIgnoreCase(result)) {
                log.info("Redis连接测试成功");
            } else {
                log.warn("Redis连接测试异常，返回: {}", result);
                throw new RuntimeException("Redis响应异常: " + result);
            }
        } catch (Exception e) {
            log.error("Redis连接测试失败: {}", e.getMessage(), e);
            throw new RuntimeException("Redis连接测试失败", e);
        }
    }
}
