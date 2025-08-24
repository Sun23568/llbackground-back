package com.llback.common.types;

import com.llback.common.util.AssertUtil;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * 标识
 * 0否 1是
 */
public class Flag extends StringId implements Serializable {
    /**
     * 标识
     */
    private static final Pattern PATTERN = Pattern.compile("^[01]$");

    /**
     * 构造函数
     *
     * @param flag 标识ID
     */
    protected Flag(CharSequence flag) {
        super(flag, PATTERN, 128, "标识");
    }

    @Override
    protected String getRuleMsg() {
        return "仅允许0或1";
    }


    /**
     * 构造函数
     *
     * @param flag 标识ID
     */
    public static Flag of(CharSequence flag) {
        return new Flag(cleanInput(flag.toString()));
    }

    /**
     * clean输入的标识
     *
     * @param flag 标识
     * @return 去除空格后的标识ID
     */
    public static String cleanInput(String flag) {
        AssertUtil.notNull(flag, "标识不可为空");
        return flag.trim();
    }
}
