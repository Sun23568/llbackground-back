package com.llback.common.types;


import java.io.Serializable;

/**
 * 用户名称
 *
 * @author miaoxiaoming
 * @date 2024/1/9 11:03 AM
 */
public final class UserName extends SafeText implements Serializable {

    /**
     * 空值
     */
    public static final UserName EMPTY_NAME = new UserName();


    /**
     * 构造函数
     *
     * @param id 用户名称
     */
    private UserName(CharSequence id) {
        super(id, 30, "用户名称");
    }

    /**
     * 构造函数(空值)
     */
    private UserName() {
        super();
    }

    /**
     * 构造函数
     *
     * @param id 用户名称
     * @return 用户名称
     */
    public static UserName of(CharSequence id) {
        if (StringId.isEmpty(id)) {
            return UserName.EMPTY_NAME;
        }
        return new UserName(id);
    }
}
