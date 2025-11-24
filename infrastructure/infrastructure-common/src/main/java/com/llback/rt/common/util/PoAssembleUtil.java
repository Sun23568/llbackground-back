package com.llback.rt.common.util;

import com.github.pagehelper.PageInfo;
import com.llback.common.types.SafeText;
import com.llback.common.types.StringId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 缓存工具类
 */
@Slf4j
public class PoAssembleUtil {
    /**
     * po转为dto
     *
     * @author yz.sun
     * @date 2025/8/22
     */
    public static <T> T poToDto(Object poObj, Class<T> dtoClass) {
        if (poObj == null) {
            return null;
        }
        try {
            T dtoObj = dtoClass.newInstance();
            BeanUtils.copyProperties(poObj, dtoObj);
            return dtoObj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * eo转为po
     *
     * @param eoObj
     * @param poClassType
     * @param <T>
     */
    public static <T> T eo2Po(Object eoObj, Class<T> poClassType) {
        try {
            // po实例化
            T poObj = poClassType.getDeclaredConstructor().newInstance();
            // 取出eo对象的所有字段
            Field[] fields = eoObj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(eoObj);
                if (value != null) {
                    // 获取po对象中同名字段
                    Field poField;
                    try {
                        poField = poClassType.getDeclaredField(field.getName());
                    } catch (NoSuchFieldException ignored) {
                        continue;
                    }
                    poField.setAccessible(true);
                    // 设置po对象中同名字段的值
                    if (poField.getType().equals(field.getType())) {
                        poField.set(poObj, value);
                    } else if ((StringId.class.isAssignableFrom(field.getType()) || SafeText.class.isAssignableFrom(field.getType())) && poField.getType().equals(String.class)) {
                        poField.set(poObj, value.toString());
                    }
                }
            }
            return poObj;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance", e);
        }
    }

    /*
     * po转为eo
     */
    public static <T> T po2Eo(Object poObj, Class<T> eoClassType) {
        if (poObj == null) {
            return null;
        }
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
                    // value判空
                    if (isEmpty(value)) {
                        continue;
                    }

                    // 通过build方式进行复制
                    Class<?> builderFieldType = builderClass.getDeclaredField(poField.getName()).getType();
                    Class<?> poFieldType = poField.getType();
                    Method declaredMethod = builderClass.getDeclaredMethod(poField.getName(), builderFieldType);
                    if (builderFieldType.isAssignableFrom(poFieldType)) {
                        declaredMethod.invoke(builder, value);
                    } else if ((StringId.class.isAssignableFrom(builderFieldType) || SafeText.class.isAssignableFrom(builderFieldType)) && poFieldType.equals(String.class)) {
                        declaredMethod.invoke(builder, builderFieldType.getDeclaredMethod("of", CharSequence.class).invoke(builderFieldType, value));
                    }
                } catch (Exception e) {
                    // 忽略无法复制的属性
                    log.error("Failed to create instance using @Builder {}", poField);
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

    public static <T> PageInfo poPage2EoPage(PageInfo poPageObj, Class<T> eoClassType) {
        PageInfo<T> tPageInfo = PageInfo.of(poList2EoList(poPageObj.getList(), eoClassType));
        tPageInfo.setTotal(poPageObj.getTotal());
        return tPageInfo;
    }

    private static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return StringUtils.isEmpty((String) obj);
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        return false;
    }

    /**
     * poList转为dtoList
     */
    public static <T> List<T> poList2DtoList(List poObj, Class<T> dtoClassType) {
        List<T> dtoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(poObj)) {
            return dtoList;
        }
        for (Object o : poObj) {
            T t = poToDto(o, dtoClassType);
            dtoList.add(t);
        }
        return dtoList;
    }
}
