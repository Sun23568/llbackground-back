package com.llback.common.types;

import com.llback.common.util.AssertUtil;
import lombok.Getter;

import java.io.Serializable;

/**
 * 用于记录操作者ID信息
 */
@Getter
public final class OperationId implements Serializable {
    /**
     * 空操作者
     */
    public static final OperationId NULL = new OperationId(UserId.NULL_UID, UserName.EMPTY_NAME);

    /**
     * 操作者用户账号
     */
    private final UserId userId;


    /**
     * 操作者用户名称
     */
    private final UserName userName;

    /**
     * 判断是否为空操作者
     *
     * @return true/false
     */
    public boolean isNullId() {
        return NULL.equals(this);
    }

    /**
     * 计算hashcode
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * 格式化输出
     *
     * @return 格式化输出
     */
    @Override
    public String toString() {
        return String.format("{userId=%s,userName=%s}", this.userId, this.userName);
    }

    /**
     * 相等判断
     *
     * @param obj 待比较对象
     * @return true 相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperationId) {
            return this.userId.equals(((OperationId) obj).userId);
        }
        return false;
    }

    /**
     * 构造函数
     *
     * @param userId   操作者用户账号
     * @param userName 操作者用户名称
     */
    private OperationId(UserId userId, UserName userName) {
        this.userId = userId;
        this.userName = userName;
    }


    /**
     * 构造函数
     *
     * @param userId   操作者用户账号
     * @param userName 操作者用户名称
     * @return 操作者
     */
    public static OperationId of(UserId userId, UserName userName) {
        AssertUtil.assertTrue(null != userId && !userId.isEmpty(), "userId不能为空");
        AssertUtil.assertTrue(null != userName && !userName.isEmpty(), "userName不能为空");
        return new OperationId(userId, userName);
    }
}
