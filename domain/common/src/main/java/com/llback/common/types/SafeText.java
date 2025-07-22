package com.llback.common.types;

import com.llback.common.util.XssUtil;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * css @2024
 * 该类用于存储安全文本内容，防止XSS攻击
 *
 * @author hex
 * @date 2023/11/9 18:31
 */

public class SafeText extends BaseValidText {
    /**
     * 校验是否为非法的文本内容
     */
    private static final Predicate<CharSequence> FN_XSS_CHECK = value -> !XssUtil.existsXssText(value);

    /**
     * 空文本
     */
    public static final SafeText EMPTY = new SafeText();

    /**
     * 构造空对象
     */
    protected SafeText() {
        super();
    }

    /**
     * 构造函数
     *
     * @param value 文本内容
     */
    protected SafeText(CharSequence value) {
        this(value, 1024);
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     */
    protected SafeText(CharSequence text, int maxLen) {
        this(text, maxLen, "文本内容");
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     * @param tag    错误消息
     */
    protected SafeText(CharSequence text, int maxLen, String tag) {
        super(text, FN_XSS_CHECK, maxLen, tag);
    }

    /**
     * 构造函数
     *
     * @param text    文本内容
     * @param pattern 正则表达式
     * @param maxLen  最大长度
     * @param tag     错误消息
     */
    protected SafeText(CharSequence text, Pattern pattern, int maxLen, String tag) {
        super(text, pattern, maxLen, tag);
    }

    /**
     * 构造函数
     *
     * @param text 文本内容
     */
    public static SafeText of(CharSequence text) {
        if (StringId.isEmpty(text)) {
            return EMPTY;
        }
        return new SafeText(text);
    }

    /**
     * 构造函数
     *
     * @param text 文本内容
     */
    public static SafeText of(long text) {
        return new SafeText(String.valueOf(text));
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     */
    public static SafeText of(CharSequence text, int maxLen) {
        if (StringId.isEmpty(text)) {
            return EMPTY;
        }
        return new SafeText(text, maxLen);
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     * @param tag    错误消息
     */
    public static SafeText of(CharSequence text, int maxLen, String tag) {
        if (StringId.isEmpty(text)) {
            return EMPTY;
        }
        return new SafeText(text, maxLen, tag);
    }
}
