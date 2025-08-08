package com.llback.common.util;

import com.llback.common.exception.BizException;
import com.llback.common.exception.ErrorCode;
import lombok.experimental.UtilityClass;

import javax.security.auth.login.LoginException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * 输入参数校验工具类
 *
 * @author 作者 hex
 * Date: 2023/11/08
 * Time: 9:15
 */
@UtilityClass
public class AssertUtil {

    /**
     * 输入字符不能为空
     *
     * @param input   input string
     * @param message message
     */
    public static void notEmpty(CharSequence input, String message) {
        if (null == input || input.length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 输入字符不能为空
     *
     * @param input   input string
     * @param fnThrow 异常
     */
    public static <T extends Throwable> void notEmpty(CharSequence input, Supplier<T> fnThrow) throws T {
        if (null == input || input.length() == 0) {
            throw fnThrow.get();
        }
    }

    /**
     * 检验集合不能为空
     *
     * @param list    input list
     * @param message message
     */
    public static void notEmptyList(Collection<?> list, String message) {
        if (null == list || list.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检验集合不能为空
     *
     * @param list    input list
     * @param fnThrow 异常
     */
    public static <T extends Throwable> void notEmptyList(Collection<?> list, Supplier<T> fnThrow) throws T {
        if (null == list || list.isEmpty()) {
            throw fnThrow.get();
        }
    }

    /**
     * 输入对象不能为空
     *
     * @param input   input
     * @param message message
     */
    public static <T> T notNull(T input, String message) {
        if (null == input) {
            throw new IllegalArgumentException(message);
        }
        return input;
    }

    /**
     * 输入对象不能为空
     *
     * @param input             input
     * @param exceptionSupplier exceptionSupplier
     */
    public static <T, E extends Exception> T notNull(T input, Supplier<E> exceptionSupplier) throws E {
        if (null == input) {
            throw exceptionSupplier.get();
        }
        return input;
    }

    /**
     * 输入条件必须成立
     *
     * @param condition input
     * @param message   message
     */
    public static void assertTrue(boolean condition, String message) throws IllegalArgumentException {
        if (!condition) {
            throw new BizException(ErrorCode.LOGIN_USER_VERIFY_FAILED);
        }
    }

    /**
     * 输入条件必须成立
     *
     * @param condition condition
     * @param fnThrow   fn callback
     */
    public static <T extends Throwable> void assertTrue(boolean condition, Supplier<T> fnThrow) throws T {
        if (!condition) {
            throw fnThrow.get();
        }
    }

    /**
     * 输入条件不成立
     *
     * @param condition input
     * @param message   message
     */
    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 输入条件不成立
     *
     * @param condition input
     * @param fnThrow   异常
     */
    public static <T extends Throwable> void assertFalse(boolean condition, Supplier<T> fnThrow) throws T {
        if (condition) {
            throw fnThrow.get();
        }
    }

    /**
     * 输入数值是否与期望值相同
     *
     * @param actual   input value
     * @param expected valid value
     * @param message  message
     */
    public static void assertEquals(int actual, int expected, String message) {
        assertTrue(actual == expected, message);
    }

    /**
     * 输入数值是否与期望值相同
     *
     * @param actual   input value
     * @param expected valid value
     * @param fnThrow  异常
     */
    public static <T extends Throwable> void assertEquals(int actual, int expected, Supplier<T> fnThrow) throws T {
        assertTrue(actual == expected, fnThrow);
    }

    /**
     * 输入值是否在between范围
     *
     * @param actual  input value
     * @param begin   min value
     * @param end     max value
     * @param message message
     */
    public static void assertBetween(int actual, int begin, int end, String message) {
        assertTrue(actual >= begin && actual <= end, message);
    }

    /**
     * 输入值是否在between范围
     *
     * @param actual  input value
     * @param begin   min value
     * @param end     max value
     * @param fnThrow 异常
     */
    public static <T extends Throwable> void assertBetween(int actual, int begin, int end, Supplier<T> fnThrow) throws T {
        assertTrue(actual >= begin && actual <= end, fnThrow);
    }

    /**
     * 输入的字符串长度是否在指定的范围
     *
     * @param input   input string
     * @param begin   min length
     * @param end     max length
     * @param message message
     */
    public static void assertInputLength(CharSequence input, int begin, int end, String message) {
        if (begin > 0) {
            notEmpty(input, message);
        }
        assertBetween(input.length(), begin, end, message);
    }

    /**
     * 输入的字符串长度是否在指定的范围
     *
     * @param input   input string
     * @param begin   min length
     * @param end     max length
     * @param fnThrow 异常
     */
    public static <T extends Throwable> void assertInputLength(CharSequence input, int begin, int end, Supplier<T> fnThrow) throws T {
        if (begin > 0) {
            notEmpty(input, fnThrow);
        }
        assertBetween(input.length(), begin, end, fnThrow);
    }

    /**
     * 输入的字符是否为数字
     *
     * @param input   input string
     * @param message message
     */
    public static void assertInputDigits(String input, String message) {
        notEmpty(input, message);
        assertTrue(input.matches("^\\d+$"), message);
    }

    /**
     * 输入的字符是否为数字
     *
     * @param input   input string
     * @param fnThrow 异常
     */
    public static <T extends Throwable> void assertInputDigits(String input, Supplier<T> fnThrow) throws T {
        notEmpty(input, fnThrow);
        assertTrue(input.matches("^\\d+$"), fnThrow);
    }

    /**
     * 必填
     *
     * @param input   input string
     * @param argName argument name
     */
    public static void required(String input, String argName) {
        AssertUtil.notEmpty(input, String.format("参数[%s]不能为空", argName));
    }

    /**
     * 必输
     *
     * @param input   input string
     * @param argName argument name
     */
    public static void required(Object input, String argName) {
        String msg = String.format("参数[%s]不能为空", argName);
        if (input instanceof String) {
            AssertUtil.notBlank((String) input, msg);
        } else {
            AssertUtil.notNull(input, msg);
        }
    }

    /**
     * 输入字符不能为空，并且不是存空格
     *
     * @param input   input string
     * @param message message
     */
    public static void notBlank(String input, String message) {
        if (null == input || input.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 输入字符不能为空，并且不是存空格
     *
     * @param input   input string
     * @param fnThrow 异常
     */
    public static <T extends Throwable> void notBlank(String input, Supplier<T> fnThrow) throws T {
        if (null == input || input.trim().isEmpty()) {
            throw fnThrow.get();
        }
    }

    /**
     * 必输且输入最大长度控制
     *
     * @param input   input string
     * @param maxLen  max length
     * @param argName argument name
     */
    public static void requiredAndMaxLen(CharSequence input, int maxLen, String argName) {
        required(input, argName);
        limitMaxLenArg(input, maxLen, argName);
    }

    /**
     * 输入最大长度控制
     *
     * @param input   input string
     * @param maxLen  max length
     * @param argName argument name
     */
    public static void limitMaxLenArg(CharSequence input, int maxLen, String argName) {
        if (null == input) {
            return;
        }
        AssertUtil.assertInputLength(input, 0, maxLen, String.format("参数[%s]最大长度为:%d", argName, maxLen));
    }

    /**
     * 输入固定长度控制
     *
     * @param input    input str
     * @param fixedLen length
     * @param argName  argument name
     */
    public static void requiredAndFixedLen(String input, int fixedLen, String argName) {
        required(input, argName);
        AssertUtil.assertInputLength(input, fixedLen, fixedLen, String.format("参数[%s]长度必须为:%d", argName, fixedLen));
    }

    /**
     * 检验两个BigDecimal必须相同
     *
     * @param value1 value1
     * @param value2 value2
     * @param msg    error msg
     */
    public static void assertEquals(BigDecimal value1, BigDecimal value2, String msg) {
        if (value1.compareTo(value2) != 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 检验两个BigDecimal必须相同
     *
     * @param value1  value1
     * @param value2  value2
     * @param fnThrow 异常
     */
    public static <T extends Throwable> void assertEquals(BigDecimal value1, BigDecimal value2, Supplier<T> fnThrow) throws T {
        if (value1.compareTo(value2) != 0) {
            throw fnThrow.get();
        }
    }

    /**
     * 校验两个字符串必须相同
     *
     * @param input1 string1
     * @param input2 string2
     * @param msg    error msg
     */
    public static void assertEquals(String input1, String input2, String msg) {
        String s1 = null == input1 ? "" : input1;
        String s2 = null == input2 ? "" : input2;

        if (!s1.contentEquals(s2)) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 校验两个字符串必须相同
     *
     * @param input1  string1
     * @param input2  string2
     * @param fnThrow 异常
     */
    public static <T extends Throwable> void assertEquals(String input1, String input2, Supplier<T> fnThrow) throws T {
        String s1 = null == input1 ? "" : input1;
        String s2 = null == input2 ? "" : input2;

        if (!s1.contentEquals(s2)) {
            throw fnThrow.get();
        }
    }


}
