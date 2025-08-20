package com.llback.dal.user.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息的数据传输对象（Po）
 * 用于持久化用户相关的基本信息
 */
@Data
public class UserPo {
    /**
     * 主键ID
     * 使用String类型以适应各种数据库的ID格式
     */
    private String pkId;

    /**
     * 用户ID
     * 唯一标识用户，不同于主键ID，可能用于业务逻辑中的用户标识
     */
    private String userId;

    /**
     * 用户名
     * 存储用户的名称，用于显示或查询
     */
    private String userName;

    /**
     * 密码
     * 存储用户的密码，应加密存储以保证安全
     */
    private String password;

    /**
     * 创建时间
     * 记录用户信息创建的时间，用于审计和历史追踪
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     * 记录最后一次修改用户信息的时间，用于审计和同步
     */
    private LocalDateTime updateTime;
}
