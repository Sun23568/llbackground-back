package com.llback.rt.common.cache;

/**
 * 缓存类型
 */
public enum CacheType{
    USER("user", "用户缓存");

    /**
     * 缓存ID
     */
    private String id;

    /**
     * 缓存名称
     */
    private String name;

    CacheType(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
