package com.llback.dal.sa.po;

import lombok.Data;

/**
 * 用户信息的数据传输对象（Po）
 * 用于持久化用户相关的基本信息
 */
@Data
public class UserPo {
    /**
     * 主键ID，唯一标识一条用户记录
     */
    private String id;

    /**
     * 用户编号，业务系统中的用户唯一标识
     */
    private String userId;

    /**
     * 用户名，用于登录和展示使用
     */
    private String username;

    /**
     * 密码，经过加密处理后存储
     */
    private String password;
}
