package com.llback.api.app.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息
 */
@Data
public class UserDto {
    /**
     * 主键
     */
    private String pkId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
