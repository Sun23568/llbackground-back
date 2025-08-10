package com.llback.api.util;

import com.llback.common.types.StringId;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class DtoEoAssemblerUtil {

    /**
     * 将源对象的属性值复制到目标对象
     *
     * @param source 源对象
     * @param target 目标对象
     */
    private static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        // 获取源对象的所有字段
        Field[] sourceFields = sourceClass.getDeclaredFields();

        for (Field sourceField : sourceFields) {
            try {
                // 获取源字段的值
                sourceField.setAccessible(true);
                Object value;
                if (StringId.class.isAssignableFrom(sourceField.getType())) {
                    value = sourceField.get(source).toString();
                } else {
                    value = sourceField.get(source);
                }

                // 在目标类中查找同名字段
                Field targetField;
                try {
                    targetField = targetClass.getDeclaredField(sourceField.getName());
                    targetField.setAccessible(true);

                    // 设置目标字段的值
                    if (isAssignable(targetField.getType(), sourceField.getType())) {
                        targetField.set(target, value);
                    }
                } catch (NoSuchFieldException e) {
                    // 如果目标类中没有同名字段，则尝试通过setter方法设置
                    String setterName = "set" + capitalize(sourceField.getName());
                    Method setterMethod = null;
                    try {
                        setterMethod = targetClass.getMethod(setterName, sourceField.getType());
                        setterMethod.invoke(target, value);
                    } catch (NoSuchMethodException ignored) {
                        // 忽略没有对应setter方法的情况
                    }
                }
            } catch (Exception e) {
                // 忽略单个字段复制失败的情况
            }
        }
    }

    /**
     * 检查目标类型是否可以赋值源类型
     *
     * @param targetType 目标类型
     * @param sourceType 源类型
     * @return 是否可以赋值
     */
    private static boolean isAssignable(Class<?> targetType, Class<?> sourceType) {
        if (targetType.isAssignableFrom(sourceType)) {
            return true;
        }

        // 处理基本类型和包装类型之间的转换
        if (targetType.isPrimitive()) {
            return isPrimitiveAssignable(targetType, sourceType);
        }

        // 处理值类型转String
        if (StringId.class.isAssignableFrom(sourceType) && String.class.isAssignableFrom(targetType)) {
            return true;
        }

        return false;
    }

    /**
     * 检查基本类型是否可以赋值
     *
     * @param primitiveType 基本类型
     * @param sourceType    源类型
     * @return 是否可以赋值
     */
    private static boolean isPrimitiveAssignable(Class<?> primitiveType, Class<?> sourceType) {
        if (primitiveType == int.class && sourceType == Integer.class) return true;
        if (primitiveType == long.class && sourceType == Long.class) return true;
        if (primitiveType == double.class && sourceType == Double.class) return true;
        if (primitiveType == float.class && sourceType == Float.class) return true;
        if (primitiveType == boolean.class && sourceType == Boolean.class) return true;
        if (primitiveType == byte.class && sourceType == Byte.class) return true;
        if (primitiveType == char.class && sourceType == Character.class) return true;
        if (primitiveType == short.class && sourceType == Short.class) return true;
        return false;
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 首字母大写的字符串
     */
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * EO 转换为  DTO
     *
     * @param source      源对象
     * @param targetClass 目标类
     * @return 复制后的目标对象
     */
    public static <T> T eo2Dto(Object source, Class<T> targetClass) {
        try {
            // 实例化目标对象
            T target = targetClass.getDeclaredConstructor().newInstance();
            // 复制属性
            copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate and copy properties", e);
        }
    }

    /**
     * poList 转为 eoList
     */
    public static <T> List<T> eoList2DtoList(List source, Class<T> targetClass) {
        return (List<T>) source.stream().map(item -> eo2Dto(item, targetClass)).collect(Collectors.toList());
    }
}