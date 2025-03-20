package com.heeexy.example.util;

public class UUIDUtils {
    public static String getUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
