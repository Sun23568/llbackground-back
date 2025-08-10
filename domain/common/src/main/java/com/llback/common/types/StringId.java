package com.llback.common.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * css @2024
 * 该类用于存储满足正则规范的字符串ID。默认长度为1到32的由字母、数字、下划线组成，子类可以继承该类，并重写构造函数，以满足不同的ID规则。
 *
 * @author hex
 * @date 2023/11/9 18:31
 */
public class StringId extends BaseValidText implements Serializable {

    /**
     * 正则表达式，用于验证字符串ID的规则。
     */
    private static final Pattern PATTERN = Pattern.compile("^\\w+$");

    /**
     * 默认最大长度。
     */
    private static final int DEF_MAX_LEN = 32;


    /**
     * 空的StringId对象。
     */
    public static final StringId EMPTY = new StringId();


    /**
     * 构造函数，用于创建一个StringId对象。
     *
     * @param id 字符串ID值
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    protected StringId(CharSequence id) {
        this(id, DEF_MAX_LEN);
    }

    /**
     * 构造函数，用于创建一个空对象。
     */
    protected StringId() {
        super();
    }


    /**
     * 构造函数，用于创建一个StringId对象。
     *
     * @param id     字符串ID值
     * @param maxLen 最大长度,如maxLen<=0则不验证长度
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    protected StringId(CharSequence id, int maxLen) {
        this(id, PATTERN, maxLen, "ID值");
    }


    /**
     * 构造函数，用于创建一个StringId对象。
     *
     * @param id  字符串ID值
     * @param tag 错误消息
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    protected StringId(CharSequence id, String tag) {
        this(id, PATTERN, tag);
    }

    /**
     * 构造函数，用于创建一个StringId对象。
     *
     * @param id     字符串ID值
     * @param maxLen 最大长度,如maxLen<=0则不验证长度
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    protected StringId(CharSequence id, int maxLen, String tag) {
        this(id, PATTERN, maxLen, tag);
    }

    /**
     * 构造函数，用于创建一个StringId对象。
     *
     * @param id      字符串ID值
     * @param pattern 正则表达式，用于验证字符串ID的规则
     * @param tag     错误消息
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    protected StringId(CharSequence id, Pattern pattern, String tag) {
        this(id, pattern, DEF_MAX_LEN, tag);
    }


    /**
     * 构造函数，用于创建一个StringId对象。
     *
     * @param id      字符串ID值
     * @param pattern 正则表达式，用于验证字符串ID的规则
     * @param maxLen  最大长度,如maxLen<=0则不验证长度
     * @param tag     错误消息
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    protected StringId(CharSequence id, Pattern pattern, int maxLen, String tag) {
        super(id, pattern, maxLen, tag);
    }

    /**
     * 构造函数，用于创建一个StringId对象。
     *
     * @param id      字符串ID值
     * @param fnCheck 校验函数
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    protected StringId(CharSequence id, Consumer<CharSequence> fnCheck) {
        super(id, fnCheck);
    }

    /**
     * 静态工厂方法，用于创建一个StringId对象。
     *
     * @param id 字符串ID值
     * @return 一个StringId对象
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    public static StringId of(CharSequence id) {
        return new StringId(id);
    }

    /**
     * 静态工厂方法，用于创建一个StringId对象。
     *
     * @param id  字符串ID值
     * @param tag 错误消息
     * @return 一个StringId对象
     * @throws IllegalArgumentException 如果id为null或不符合ID的规则，则抛出异常
     */
    public static StringId of(CharSequence id, String tag) {
        return new StringId(id, tag);
    }

    /**
     * 判断字符串是否为空。
     *
     * @param text 字符串
     * @return true表示为空，false表示不为空
     */
    public static boolean isEmpty(CharSequence text) {
        return null == text || text.length() == 0;
    }

    /**
     * 按照分隔符分割字符串。(不使用String.split方法，避免使用正则表达式)
     *
     * @param text      字符串
     * @param separator 分隔符
     * @return 字符串数组
     */
    public static String[] split(String text, char separator) {
        ArrayList<String> list = new ArrayList<>();
        int off = 0;
        int next;
        while ((next = text.indexOf(separator, off)) != -1) {
            list.add(text.substring(off, next));
            off = next + 1;
        }
        if (off == 0) {
            return new String[]{text};
        } else {
            // add last one
            list.add(text.substring(off));
            return list.toArray(new String[0]);
        }
    }
}
