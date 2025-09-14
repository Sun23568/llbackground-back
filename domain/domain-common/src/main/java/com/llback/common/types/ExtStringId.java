package com.llback.common.types;

import java.util.regex.Pattern;

/**
 * css @2024
 * 该类用于存储满足正则规范的字符串ID。默认长度为1到32的由字母、数字、下划线、@、点号和短划线组成，子类可以继承该类，并重写构造函数，以满足不同的ID规则。
 *
 * @author hex
 * @date 2023/11/9 18:31
 */

public class ExtStringId extends BaseValidText {

    /**
     * 正则表达式，用于验证字符串ID的规则。
     */
    private static final Pattern EXT_PATTERN = Pattern.compile("^[\\w/.:@#-]{1,64}$");

    /**
     * 构造函数，用于创建一个ExtStringId对象。
     *
     * @param id 字符串ID值
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    protected ExtStringId(CharSequence id) {
        this(id, 32);
    }

    /**
     * 构造函数，用于创建一个ExtStringId对象。
     *
     * @param id  字符串ID值
     * @param tag 错误消息
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    public ExtStringId(CharSequence id, String tag) {
        super(id, EXT_PATTERN, tag);
    }

    /**
     * 构造函数，用于创建一个StringId对象。
     *
     * @param id     字符串ID值
     * @param maxLen 最大长度,如maxLen<=0则不验证长度
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    protected ExtStringId(CharSequence id, int maxLen) {
        super(id, EXT_PATTERN, maxLen, "ExtId");
    }

    /**
     * 构造函数，用于创建一个ExtStringId对象。
     *
     * @param id     字符串ID值
     * @param maxLen 最大长度,如maxLen<=0则不验证长度
     * @param tag    错误
     */
    protected ExtStringId(CharSequence id, int maxLen, String tag) {
        super(id, EXT_PATTERN, maxLen, tag);
    }

    /**
     * 静态工厂方法，用于创建一个ExtStringId对象。
     *
     * @param id 字符串ID值
     * @return 一个ExtStringId对象
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    public static ExtStringId of(CharSequence id) {
        return new ExtStringId(id);
    }

    /**
     * 静态工厂方法，用于创建一个ExtStringId对象。
     *
     * @param id  字符串ID值
     * @param tag 错误
     * @return 一个ExtStringId对象
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    public static ExtStringId of(CharSequence id, String tag) {
        return new ExtStringId(id, tag);
    }
}
