package com.llback.rt.common.cache;

import com.llback.common.types.StringId;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 缓存工具类
 */
public class PoEoAssembleUtil {
    /*
     * po转为eo
     */
    public static <T> T po2Eo(Object poObj, Class<T> eoClassType) {
        try {
            // 获取builder方法
            Method builderMethod = eoClassType.getDeclaredMethod("builder");
            Object builder = builderMethod.invoke(null);
            Class<?> builderClass = builder.getClass();

            // 获取源对象的类
            Class<?> poClass = poObj.getClass();

            // 获取源对象的所有字段
            Field[] poFields = poClass.getDeclaredFields();

            // 遍历源对象的字段，尝试复制到目标对象
            for (Field poField : poFields) {
                try {
                    poField.setAccessible(true);
                    Object value = poField.get(poObj);

                    // 通过build方式进行复制
                    Class<?> builderFieldType = builderClass.getDeclaredField(poField.getName()).getType();
                    Class<?> poFieldType = poField.getType();
                    Method declaredMethod = builderClass.getDeclaredMethod(poField.getName(), builderFieldType);
                    if (builderFieldType.isAssignableFrom(poFieldType)) {
                        declaredMethod.invoke(builder, value);
                    } else if (StringId.class.isAssignableFrom(builderFieldType) && poFieldType.equals(String.class)) {
                        declaredMethod.invoke(builder, builderFieldType.getDeclaredMethod("of", String.class).invoke(builderFieldType, value));
                    }
                } catch (Exception e) {
                    // 忽略无法复制的属性
                }
            }

            // 获取builder的build方法
            Method buildMethod = builder.getClass().getDeclaredMethod("build");

            // 构建实例
            return (T) buildMethod.invoke(builder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance using @Builder", e);
        }
    }

    /**
     * po转为eo List
     */
    public static <T> List<T> poList2EoList(List poObj, Class<T> eoClassType) {
        List<T> eoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(poObj)) {
            return eoList;
        }
        for (Object o : poObj) {
            T t = po2Eo(o, eoClassType);
            eoList.add(t);
        }
        return eoList;
    }
}
