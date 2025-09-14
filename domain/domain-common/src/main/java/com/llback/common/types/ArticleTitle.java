package com.llback.common.types;

import com.llback.common.util.AssertUtil;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * 文章标题
 *
 * @author yz.sun
 * @date 2024/5/9
 */
public class ArticleTitle extends SafeText implements Serializable {
    /**
     * 构造函数
     *
     * @param title 菜单码ID
     */
    protected ArticleTitle(CharSequence title) {
        super(title, 255, "文章标题");
    }


    /**
     * 构造函数
     *
     * @param title 菜单码ID
     */
    public static ArticleTitle of(CharSequence title) {
        return new ArticleTitle(cleanInput(title));
    }

    /**
     * clean输入的菜单码
     *
     * @param code 菜单码
     * @return 去除空格后的菜单码ID
     */
    public static String cleanInput(CharSequence code) {
        AssertUtil.notNull(code, "文章标题不能为空");
        return code.toString().trim();
    }
}

