package com.heeexy.example.util;

/**
 * 断言工具类
 */
public class AssertUtils {
    /**
     * 断言对象是否为空
     *
     * @param object
     * @param paramName
     */
    public static void assertNotEmpty(Object object, String paramName) {
        if (object == null || object.toString().length() == 0) {
            throw new RuntimeException(paramName + "不能为空");
        }
    }

    /**
     * 断言对象是否为空
     *
     * @author yz.sun
     * @date 2025/3/27
     */
    public static void assertNotNull(Object object, String errorMsg) {
        if (object == null) {
            throw new RuntimeException(errorMsg);
        }
    }

    public static void assertTrue(boolean success, String errorMsg) {
        if (!success) {
            throw new RuntimeException(errorMsg);
        }
    }
}
