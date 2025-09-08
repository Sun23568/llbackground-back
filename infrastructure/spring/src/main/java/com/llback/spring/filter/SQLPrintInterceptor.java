package com.llback.spring.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * SQL打印拦截器，用于打印完整的SQL语句（带参数）
 */
@Slf4j
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
public class SQLPrintInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        // 格式化SQL，将参数替换进去
        String formattedSql = formatSql(sql, parameterObject, parameterMappings);

        log.info("======= 执行SQL开始 =======");
        log.info("完整SQL: \n{}", formattedSql);
        log.info("======= 执行SQL结束 =======");

        return invocation.proceed();
    }

    /**
     * 格式化SQL，将参数值替换到SQL中
     *
     * @param sql               原始SQL
     * @param parameterObject   参数对象
     * @param parameterMappings 参数映射列表
     * @return 格式化后的SQL
     */
    private String formatSql(String sql, Object parameterObject, List<ParameterMapping> parameterMappings) {
        if (sql == null || sql.trim().isEmpty() || parameterMappings == null || parameterMappings.isEmpty()) {
            return sql;
        }

        String formattedSql = sql;
        try {
            for (ParameterMapping parameterMapping : parameterMappings) {
                Object value = null;
                String propertyName = parameterMapping.getProperty();

                if (parameterObject == null) {
                    continue;
                } else if (parameterObject instanceof Map) {
                    // 处理Map类型的参数
                    Map<?, ?> map = (Map<?, ?>) parameterObject;
                    if (map.containsKey(propertyName)) {
                        value = map.get(propertyName);
                    } else {
                        // 处理MyBatis foreach循环生成的参数名，如 __frch_i_0
                        value = findValueInMap(map, propertyName);
                    }
                } else if (parameterObject instanceof String) {
                    value = parameterObject;
                } else {
                    // 处理普通对象的参数
                    Field field = getField(parameterObject.getClass(), propertyName);
                    if (field != null) {
                        field.setAccessible(true);
                        value = field.get(parameterObject);
                    }
                }

                // 替换参数值
                formattedSql = formattedSql.replaceFirst("\\?", formatValue(value));
            }
        } catch (Exception e) {
            log.warn("SQL参数格式化失败: {}", e.getMessage());
            return sql;
        }

        return formattedSql;
    }

    /**
     * 在Map中查找参数值，处理MyBatis foreach生成的参数名
     *
     * @param map          参数Map
     * @param propertyName 属性名
     * @return 参数值
     */
    private Object findValueInMap(Map<?, ?> map, String propertyName) {
        // 处理MyBatis foreach循环生成的参数名，如 __frch_item_0, __frch_i_0 等
        if (propertyName.startsWith("__frch_")) {
            // 尝试获取原始集合参数
            // MyBatis会为foreach循环中的参数创建 __frch_xxx_index 格式的名称
            // 其中xxx是item属性的名称，index是索引

            // 遍历map中的所有条目，寻找可能的集合参数
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();

                // 如果值是集合类型
                if (value instanceof Collection) {
                    // 检查参数名是否包含键名
                    String keyStr = key != null ? key.toString() : "";
                    if (!keyStr.isEmpty() && propertyName.contains(keyStr)) {
                        // 提取索引
                        String[] parts = propertyName.split("_");
                        if (parts.length >= 3) {
                            try {
                                int index = Integer.parseInt(parts[parts.length - 1]);
                                Collection<?> collection = (Collection<?>) value;
                                if (collection instanceof List) {
                                    List<?> list = (List<?>) collection;
                                    if (index < list.size()) {
                                        return list.get(index);
                                    }
                                } else {
                                    // 对于其他集合类型，尝试获取第index个元素
                                    int i = 0;
                                    for (Object obj : collection) {
                                        if (i == index) {
                                            return obj;
                                        }
                                        i++;
                                    }
                                }
                            } catch (NumberFormatException e) {
                                // 忽略解析错误
                            }
                        }
                    }
                }

                // 特殊处理：尝试直接使用"userIds"键
                if ("userIds".equals(key) && value instanceof List) {
                    String[] parts = propertyName.split("_");
                    if (parts.length >= 3) {
                        try {
                            int index = Integer.parseInt(parts[parts.length - 1]);
                            List<?> list = (List<?>) value;
                            if (index < list.size()) {
                                return list.get(index);
                            }
                        } catch (NumberFormatException e) {
                            // 忽略解析错误
                        }
                    }
                }
            }
        }

        // 如果无法找到匹配的值，返回null
        return null;
    }

    /**
     * 获取类中的字段（包括父类）
     *
     * @param clazz     类
     * @param fieldName 字段名
     * @return 字段对象
     */
    private Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    /**
     * 格式化参数值
     *
     * @param value 参数值
     * @return 格式化后的字符串
     */
    private String formatValue(Object value) {
        if (value == null) {
            return "NULL";
        }

        if (value instanceof String) {
            return "'" + value.toString().replace("'", "''") + "'";
        } else if (value instanceof Collection) {
            // 处理集合类型参数
            Collection<?> collection = (Collection<?>) value;
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            boolean first = true;
            for (Object item : collection) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(formatValue(item));
                first = false;
            }
            sb.append(")");
            return sb.toString();
        } else if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "'" + sdf.format((Date) value) + "'";
        } else if (value instanceof Boolean) {
            return value.toString().toUpperCase();
        } else {
            return value.toString();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以设置一些自定义属性
    }
}
