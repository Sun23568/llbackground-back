package com.llback.api.app.permission.dto;

import lombok.Data;

/**
 * 权限DTO
 */
@Data
public class PermDto {
    /**
     * 权限ID
     */
    private String permId;

    /**
     * 权限码
     */
    private String permCode;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 权限类型
     */
    private String permType;

    /**
     * 设置类型（字段名映射）
     */
    public void setType(String type) {
        this.permType = type;
    }
}
