package com.llback.common.types;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * css @2024
 * 用户账号
 *
 * @author hex
 * @since 2023/11/9 18:31
 */
public final class UserId extends StringId implements Serializable {
    /**
     * 正则表达式，允许字母、数字、点号、下划线
     */
    private static final Pattern PATTERN = Pattern.compile("^[\\w.]*$");

    /**
     * 空值
     */
    public static final UserId NULL_UID = new UserId("NULL");

    /**
     * 游客账号
     */
    public static final UserId GUEST_UID = new UserId("guest");

    /**
     * 空值
     */
    private UserId() {
        super();
    }

    /**
     * 构造函数
     *
     * @param id 用户账号
     */
    private UserId(CharSequence id) {
        super(id, PATTERN, 16, "用户ID");
    }

    /**
     * 判断是否为空
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isNullId() {
        return this.equalsIgnoreCase(NULL_UID);
    }

    /**
     * 判断是否为游客账号
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isGuest() {
        return this.equalsIgnoreCase(GUEST_UID);
    }

    /**
     * 构造函数
     *
     * @param id 用户账号
     * @return 用户账号
     */
    public static UserId of(CharSequence id) {
        if (StringId.isEmpty(id) || NULL_UID.equalsIgnoreCase(id)) {
            return NULL_UID;
        }
        if (GUEST_UID.equalsIgnoreCase(id)) {
            return GUEST_UID;
        }
        return new UserId(id);
    }

    /**
     * 判断是否是UserId
     *
     * @param text 文本内容
     * @return true:是用户Id; false:不是用户Id
     */
    @JsonIgnore
    public static boolean isUserId(CharSequence text) {
        return PATTERN.matcher(text).matches();
    }

    /**
     * 判断是否是游客账号
     *
     * @author yz.sun
     * @date 2024/5/29
     */
    @JsonIgnore
    public static boolean isGuest(CharSequence text) {
        return GUEST_UID.equalsIgnoreCase(text);
    }
}
