package com.llback.common.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.llback.common.util.AssertUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * css @2024
 * BaseValidText 类实现了Serializable、Comparable<String>和CharSequence接口，表示一个通过校验的文本类。
 *
 * @author hex
 * @date 2023/11/9 18:31
 */

public abstract class BaseValidText implements Serializable, Comparable<Object>, CharSequence {


    /**
     * 文本内容
     */
    protected final String text;

    /**
     * 默认的错误提示信息
     */
    protected static final String DEFAULT_RULE_MSG = "规则校验不通过";


    /**
     * 构造函数，用于创建一个ValidText对象。
     *
     * @param text    字符串值
     * @param pattern 正则表达式，用于验证字符串的规则
     * @param tag     错误提示Tag
     * @throws IllegalArgumentException 如果text 为null或不符合规则，则抛出异常
     */
    protected BaseValidText(CharSequence text, Pattern pattern, String tag) {
        this(text, pattern, -1, tag);
    }

    /**
     * 构造函数，用于创建一个ValidText对象。
     *
     * @param text    字符串值
     * @param pattern 正则表达式，用于验证字符串的规则
     * @param maxLen  最大长度,如maxLen<=0则不验证长度
     * @param tag     错误提示Tag
     * @throws IllegalArgumentException 如果text 为null或不符合规则，则抛出异常
     */
    protected BaseValidText(CharSequence text, Pattern pattern, int maxLen, String tag) {
        this(text, pattern, 0, maxLen, tag);
    }

    /**
     * 构造函数，用于创建一个ValidText对象。
     *
     * @param text    字符串值
     * @param pattern 正则表达式，用于验证字符串的规则
     * @param minLen  最小长度,如minLen<=0则不验证
     * @param maxLen  最大长度,如maxLen<=0则不验证
     * @param tag     错误提示TAG
     * @throws IllegalArgumentException 如果text 为null或不符合规则，则抛出异常
     */
    protected BaseValidText(CharSequence text, Pattern pattern, int minLen, int maxLen, String tag) {
        AssertUtil.notNull(text, String.format("[%s]不能为null", tag));
        AssertUtil.assertTrue((minLen <= 0 || text.length() >= minLen),
                String.format("[%s]长度不能小于%d个字符", tag, minLen));
        AssertUtil.assertTrue((maxLen <= 0 || text.length() <= maxLen),
                String.format("[%s]长度不能超过%d个字符", tag, maxLen));
        AssertUtil.assertTrue(pattern.matcher(text).matches(),
                String.format("[%s]%s", tag, this.getRuleMsg()));
        this.text = text.toString();
    }

    /**
     * 获取校验规则的错误提示信息
     *
     * @return 校验规则的错误提示信息
     */
    protected String getRuleMsg() {
        return DEFAULT_RULE_MSG;
    }


    /**
     * 构造函数，用于创建一个ValidText对象。
     *
     * @param text    文本内容
     * @param fnCheck 校验函数
     * @param maxLen  最大长度,如maxLen<=0则不验证
     * @param tag     校验不通过的错误消息
     */
    protected BaseValidText(CharSequence text, Predicate<CharSequence> fnCheck, int maxLen, String tag) {
        AssertUtil.notNull(text, String.format("[%s]不能为null", tag));
        AssertUtil.assertTrue((maxLen <= 0 || text.length() <= maxLen),
                String.format("[%s]长度不能超过%d个字符", tag, maxLen));
        AssertUtil.assertTrue(fnCheck.test(text), String.format("[%s]%s", tag, this.getRuleMsg()));
        this.text = text.toString();
    }


    /**
     * 构造函数，用于创建一个ValidText对象。
     *
     * @param text    文本内容
     * @param fnCheck 校验函数
     * @throws IllegalArgumentException 如果text为null或不符合ID的规则，则抛出异常
     */
    protected BaseValidText(CharSequence text, Consumer<CharSequence> fnCheck) {
        AssertUtil.notNull(text, "text不能为null");
        fnCheck.accept(text);
        this.text = text.toString();
    }

    /**
     * 构造函数，用于创建一个空ValidText对象。
     */
    protected BaseValidText() {
        this.text = "";
    }

    /**
     * 判断文本内容是否为空。
     *
     * @return 如果文本内容为空，则返回true，否则返回false
     */
    @JsonIgnore
    public boolean isEmpty() {
        return this.toString().isEmpty();
    }

    /**
     * 返回文本内容的长度。
     *
     * @return 文本内容的长度
     */
    @Override
    public int length() {
        return this.toString().length();
    }

    /**
     * 返回文本内容指定位置的字符。
     *
     * @param index 字符位置
     * @return 指定位置的字符
     */
    @Override
    public char charAt(int index) {
        return this.toString().charAt(index);
    }

    /**
     * 返回文本内容的一个子序列。
     *
     * @param start 子序列的起始位置
     * @param end   子序列的结束位置
     * @return 文本内容的一个子序列
     */
    @Override
    public CharSequence subSequence(int start, int end) {
        return this.toString().subSequence(start, end);
    }

    /**
     * 将文本内容与另一个字符串进行比较。
     *
     * @param o 另一个字符串
     * @return 如果此文本内容小于、等于或大于另一个字符串，则分别返回-1、0或1
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof CharSequence) {
            return this.toString().compareTo(o.toString());
        }
        return -1;
    }


    /**
     * 返回文本内容的哈希码。
     *
     * @return 文本内容的哈希码
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * 判断两个ValidText对象是否相等。
     *
     * @param anObject 另一个对象
     * @return 如果两个ValidText对象相等，则返回true，否则返回false
     */
    @Override
    public boolean equals(Object anObject) {
        // 如果anObject是CharSequence类型，则调用id的equals方法与之比较
        if (anObject instanceof CharSequence) {
            return this.equals(anObject.toString());
        }
        // 其他情况返回false
        return false;
    }

    /**
     * 判断文本内容是否等于指定字符串，忽略大小写。
     *
     * @param anObject 另一个字符串
     * @return 如果文本内容等于指定字符串，则返回true，否则返回false
     */
    public boolean equalsIgnoreCase(CharSequence anObject) {
        return StringUtils.equalsIgnoreCase(this.toString(), anObject);
    }

    /**
     * 判断文本内容是否等于指定字符串。
     *
     * @param text 文本内容
     * @return 如果文本内容等于指定字符串，则返回true，否则返回false
     */
    public boolean equals(String text) {
        return this.toString().equals(text);
    }

    /**
     * 判断文本内容是否以指定前缀开头。
     *
     * @param prefix 前缀
     * @return 如果文本内容以指定前缀开头，则返回true，否则返回false
     */
    public boolean startsWith(String prefix) {
        return this.toString().startsWith(prefix);
    }

    /**
     * 判断文本内容是否以指定后缀结尾。
     *
     * @param suffix 后缀
     * @return 如果文本内容以指定后缀结尾，则返回true，否则返回false
     */
    public boolean endsWith(String suffix) {
        return this.toString().endsWith(suffix);
    }

    /**
     * 返回文本的字符串表示。
     *
     * @return 文本内容
     */
    @Override
    public String toString() {
        return this.text;
    }

    @JsonValue
    public String getValue(){
        return toString();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // 反序列化所有非transient字段
        String test = (String) in.readObject(); // 手动反序列化敏感数据字段
        System.out.println();
    }
}

