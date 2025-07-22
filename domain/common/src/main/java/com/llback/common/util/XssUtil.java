package com.llback.common.util;

import lombok.experimental.UtilityClass;
import java.util.regex.Pattern;

/**
 * css @2023
 *
 * @author 作者hex
 */
@UtilityClass
public class XssUtil {

    /**
     * XSS_INVALID_INPUT
     */
    private static final Pattern XSS_INVALID_INPUT = Pattern.compile("((['\\\\])|(<.*script)|(eval\\s*\\())",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    /**
     * assertXssInput
     *
     * @param value value
     * @return boolean
     */
    public static boolean existsXssText(CharSequence value) {
        return null != value && XSS_INVALID_INPUT.matcher(value).find();
    }


}
