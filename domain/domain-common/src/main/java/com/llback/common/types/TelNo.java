package com.llback.common.types;

import java.util.regex.Pattern;

/**
 * TelNo
 * 电话号码对象类
 *
 * @author feifei.guo
 * @date 2023/12/27 17:36
 * @since 1.00
 */
public class TelNo extends BaseValidText {
    /**
     * 正则表达式，用于电话号码的规则
     */
    private static final Pattern TELNO_PATTERN = Pattern.compile("^\\+?[\\d-]+$");


    /**
     * 空文本
     */
    public static final TelNo EMPTY = TelNo.of("");

    /**
     * 构造函数
     *
     * @param value 文本内容
     */
    protected TelNo(CharSequence value) {
        this(value, 20);
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     */
    protected TelNo(CharSequence text, int maxLen) {
        this(text, maxLen, "电话号码");
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     * @param tag    错误消息
     */
    protected TelNo(CharSequence text, int maxLen, String tag) {
        super(text, TELNO_PATTERN, maxLen, tag);
    }

    /**
     * 构造函数
     *
     * @param text 文本内容
     */
    public static TelNo of(CharSequence text) {
        if (text == null || text.length() == 0) {
            return EMPTY;
        }
        return new TelNo(text);
    }
}

