package com.llback.common.types;

import com.llback.common.util.AssertUtil;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * css @2024
 * 功能ID
 *
 * @author hex
 * @date 2023/11/9 18:31
 */

public class FuncCode extends StringId implements Serializable {
    /**
     * 功能码
     */
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9:_.@-]*$");

    /**
     * 构造函数
     *
     * @param code 功能码ID
     */
    protected FuncCode(CharSequence code) {
        super(code, PATTERN, 128, "功能码");
    }

    @Override
    protected String getRuleMsg() {
        return "仅允许输入字母、数字、小数点、下划线、横杠、冒号、@符号";
    }


    /**
     * 构造函数
     *
     * @param code 功能码ID
     */
    public static FuncCode of(CharSequence code) {
        return new FuncCode(cleanInput(code.toString()));
    }

    /**
     * clean输入的功能码
     *
     * @param code 功能码
     * @return 去除空格后的功能码ID
     */
    public static String cleanInput(String code) {
        AssertUtil.notNull(code, "功能码不能为空");
        return code.trim();
    }
}

