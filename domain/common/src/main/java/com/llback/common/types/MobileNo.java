package com.llback.common.types;

import java.util.regex.Pattern;

/**
 * MobileNo
 * 手机号码对象类
 *
 * @author feifei.guo
 * @date 2023/12/27 17:36
 * @since 1.00
 */
public class MobileNo extends BaseValidText {
    /**
     * 正则表达式，用于手机号码的规则。
     */
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1[3456789]\\d{9}$");


    /**
     * 空
     */
    public static final MobileNo EMPTY = new MobileNo();

    /**
     * 私有构造函数(空对象)
     */
    private MobileNo() {
        super();
    }

    /**
     * 构造函数
     *
     * @param value 文本内容
     */
    protected MobileNo(CharSequence value) {
        this(value, 11);
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     */
    protected MobileNo(CharSequence text, int maxLen) {
        this(text, maxLen, "手机号码");
    }

    /**
     * 构造函数
     *
     * @param text   文本内容
     * @param maxLen 最大长度
     * @param tag    错误消息
     */
    protected MobileNo(CharSequence text, int maxLen, String tag) {
        super(text, MOBILE_PATTERN, maxLen, tag);
    }

    /**
     * 构造函数
     *
     * @param text 文本内容
     */
    public static MobileNo of(CharSequence text) {
        if (StringId.isEmpty(text)) {
            return EMPTY;
        }
        return new MobileNo(text);
    }

    /**
     * 判断是否是手机号码
     *
     * @param text 文本内容
     * @return true:是手机号码; false:不是手机号码
     */
    public static boolean isMobileNo(CharSequence text) {
        return MOBILE_PATTERN.matcher(text).matches();
    }
}

