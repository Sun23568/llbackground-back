package com.llback.common.types;

import java.util.regex.Pattern;

/**
 * xms-container @2023
 * 加密的密码串
 *
 * @author hex
 * @date 2023/11/9 18:31
 */

public class EncryptedPassword extends StringId {
    /**
     * 正则表达式，用于验证密码的规则。
     */
    private static final Pattern EN_PWD_PATTERN = Pattern.compile("^.{0,1024}$");

    /**
     * 空的StringId对象。
     */
    public static final EncryptedPassword EMPTY = new EncryptedPassword("");

    /**
     * 构造函数
     *
     * @param pwd 密码
     */
    protected EncryptedPassword(CharSequence pwd) {
        super(pwd, EN_PWD_PATTERN, 1024, "加密密码");
    }

    /**
     * 构造函数
     *
     * @param pwd 密码
     */
    public static EncryptedPassword of(CharSequence pwd) {
        if (StringId.isEmpty(pwd)) {
            return EMPTY;
        }
        return new EncryptedPassword(pwd);
    }
}

