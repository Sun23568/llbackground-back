package com.llback.common.types;

import java.util.regex.Pattern;

/**
 * xms-container @2023
 * 明文密码
 *
 * @author hex
 * @date 2023/11/9 18:31
 */

public class PlainPassword extends StringId {
    /**
     * 正则表达式，用于验证密码的规则。
     */
    private static final Pattern PWD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{}|;:',.<>/?`~]).{6,16}$");
    /**
     * 正则表达式，用于不需要校验复杂度的场景，校验复杂度用 PWD_PATTERN
     */
    private static final Pattern SIMPLE_PWD_PATTERN = Pattern.compile("^.{0,2048}$");

    /**
     * 构造函数
     *
     * @param pwd 密码
     */
    protected PlainPassword(CharSequence pwd) {
        super(pwd, SIMPLE_PWD_PATTERN, 2048, "密码");
    }

    /**
     * 构造函数
     *
     * @param pwd 密码
     */
    public static PlainPassword of(CharSequence pwd) {
        return new PlainPassword(pwd);
    }

    /**
     * 校验密码复杂度
     *
     * @author yz.sun
     * @date 2024/4/15
     */
    public boolean verityComplexity() {
        return PWD_PATTERN.matcher(this.toString()).matches();
    }
}

