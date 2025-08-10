package com.llback.common.types;

import java.util.regex.Pattern;

/**
 * css @2024
 * 邮箱值对象类
 *
 * @author feifei.guo
 * @date 2023/12/21 17:54
 * @since 1.00
 */
public class Email extends BaseValidText {
    /**
     * 正则表达式，用于验证邮箱的规则。
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");


    /**
     * 空文本
     */
    public static final Email EMPTY = Email.of("");

    /**
     * 构造函数
     *
     * @param value 文本内容
     */
    protected Email(CharSequence value) {
        this(value, 128);
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     */
    protected Email(CharSequence text, int maxLen) {
        this(text, maxLen, "邮箱地址");
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     * @param tag    错误消息
     */
    protected Email(CharSequence text, int maxLen, String tag) {
        super(text, EMAIL_PATTERN, maxLen, tag);
    }

    /**
     * 构造函数
     *
     * @param text 文本内容
     */
    public static Email of(CharSequence text) {
        if (text == null || text.length() == 0) {
            return EMPTY;
        }
        return new Email(text);
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     */
    public static Email of(CharSequence text, int maxLen) {
        return new Email(text, maxLen);
    }
}

