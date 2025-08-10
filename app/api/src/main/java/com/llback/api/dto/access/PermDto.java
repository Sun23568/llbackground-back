package com.llback.api.dto.access;

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
    private String type;
}
