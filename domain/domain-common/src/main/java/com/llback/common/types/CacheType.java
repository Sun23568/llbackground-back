package com.llback.common.types;

/**
 * 缓存类型
 */
public enum CacheType {
    USER("user", "用户缓存"),
    TOKEN("token", "Token缓存");

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

    public String getId() {
        return id;
    }

    public static CacheType of(String id) {
        for (CacheType cacheType : CacheType.values()) {
            if (cacheType.id.equals(id)) {
                return cacheType;
            }
        }
        return null;
    }
}
