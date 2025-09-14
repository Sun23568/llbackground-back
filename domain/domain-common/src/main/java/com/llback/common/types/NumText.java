package com.llback.common.types;

import java.util.regex.Pattern;

/**
 * css @2024
 * 该类用于存储由纯数字的文本，子类可以继承该类，并重写构造函数，以满足不同的规则。
 *
 * @author hex
 * @date 2023/11/9 18:31
 */
public class NumText extends BaseValidText {

    /**
     * 正则表达式，用于验证字符串编号的规则。
     */
    private static final Pattern PATTERN = Pattern.compile("^\\d+$");

    /**
     * 默认最大长度。
     */
    private static final int DEF_MAX_LEN = 32;


    /**
     * 构造函数，用于创建一个NumText对象。
     *
     * @param no 字符串编号值
     * @throws IllegalArgumentException 如果no为null或不符合编号的规则，则抛出异常
     */
    protected NumText(CharSequence no) {
        this(no, DEF_MAX_LEN);
    }


    /**
     * 构造函数，用于创建一个NumText对象。
     *
     * @param no     字符串编号值
     * @param maxLen 最大长度,如maxLen<=0则不验证长度
     * @throws IllegalArgumentException 如果no为null或不符合编号的规则，则抛出异常
     */
    protected NumText(CharSequence no, int maxLen) {
        this(no, maxLen, "数字编号");
    }


    /**
     * 构造函数，用于创建一个NumText对象。
     *
     * @param no     字符串编号值
     * @param minLen 最小长度,如maxLen<=0则不验证
     * @param maxLen 最大长度,如maxLen<=0则不验证
     * @throws IllegalArgumentException 如果no为null或不符合编号的规则，则抛出异常
     */
    protected NumText(CharSequence no, int minLen, int maxLen) {
        this(no, minLen, maxLen, "数字编号");
    }


    /**
     * 构造函数，用于创建一个NumText对象。
     *
     * @param no     字符串编号值
     * @param minLen 最小长度,如maxLen<=0则不验证
     * @param maxLen 最大长度,如maxLen<=0则不验证
     * @throws IllegalArgumentException 如果no为null或不符合编号的规则，则抛出异常
     */
    protected NumText(CharSequence no, int minLen, int maxLen, String tag) {
        super(no, PATTERN, minLen, maxLen, tag);
    }


    /**
     * 构造函数，用于创建一个NumText对象。
     *
     * @param no  字符串编号值
     * @param tag 错误消息
     * @throws IllegalArgumentException 如果no为null或不符合编号的规则，则抛出异常
     */
    protected NumText(CharSequence no, String tag) {
        super(no, PATTERN, tag);
    }

    /**
     * 构造函数，用于创建一个NumText对象。
     *
     * @param no     字符串编号值
     * @param maxLen 最大长度,如maxLen<=0则不验证长度
     * @param tag    错误消息
     * @throws IllegalArgumentException 如果no为null或不符合编号的规则，则抛出异常
     */
    protected NumText(CharSequence no, int maxLen, String tag) {
        super(no, PATTERN, maxLen, tag);
    }


    /**
     * 静态工厂方法，用于创建一个NumText对象。
     *
     * @param no 字符串编号值
     * @return 一个NumText对象
     * @throws IllegalArgumentException 如果no为null或不符合编号的规则，则抛出异常
     */
    public static NumText of(CharSequence no) {
        return new NumText(no);
    }

    /**
     * 静态工厂方法，用于创建一个NumText对象。
     *
     * @param no  字符串编号值
     * @param tag 错误消息
     * @return 一个NumText对象
     * @throws IllegalArgumentException 如果no为null或不符合编号的规则，则抛出异常
     */
    public static NumText of(CharSequence no, String tag) {
        return new NumText(no, tag);
    }

    /**
     * 静态工厂方法，用于创建一个NumText对象。
     *
     * @param no     字符串编号值
     * @param maxLen 最大长度,如maxLen<=0则不验证长度
     * @param tag    错误消息
     * @return 一个NumText对象
     * @throws IllegalArgumentException 如果no为null或不符合编号的规则，则抛出异常
     */
    public static NumText of(CharSequence no, int maxLen, String tag) {
        return new NumText(no, maxLen, tag);
    }


    /**
     * 静态工厂方法，用于创建一个NumText对象。
     *
     * @param no     字符串编号值
     * @param maxLen 最大长度,如maxLen<=0则不验证长度
     * @return 一个NumText对象
     * @throws IllegalArgumentException 如果no为null或不符合编号的规则，则抛出异常
     */
    public static NumText of(CharSequence no, int maxLen) {
        return new NumText(no, maxLen);
    }


}
