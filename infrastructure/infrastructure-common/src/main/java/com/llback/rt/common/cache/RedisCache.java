package com.llback.rt.common.cache;

import redis.clients.jedis.Jedis;

public class RedisCache {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("120.27.201.34", 6379);
        System.out.println(jedis.ping());
        String set = jedis.set("name", "llback");
        System.out.println( set);
    }
}
