package com.llback.common.types;

import com.llback.common.util.AssertUtil;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * 菜单编码
 *
 * @author yz.sun
 * @date 2024/5/9
 */
public class MenuCode extends StringId implements Serializable {
    /**
     * 菜单码
     */
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9:_.@-]*$");

    /**
     * 构造函数
     *
     * @param code 菜单码ID
     */
    protected MenuCode(CharSequence code) {
        super(code, PATTERN, 128, "菜单码");
    }

    @Override
    protected String getRuleMsg() {
        return "仅允许输入字母、数字、小数点、下划线、横杠、冒号、@符号";
    }


    /**
     * 构造函数
     *
     * @param code 菜单码ID
     */
    public static MenuCode of(CharSequence code) {
        return new MenuCode(cleanInput(code));
    }

    /**
     * clean输入的菜单码
     *
     * @param code 菜单码
     * @return 去除空格后的菜单码ID
     */
    public static String cleanInput(CharSequence code) {
        AssertUtil.notNull(code, "菜单码不能为空");
        return code.toString().trim();
    }
}

