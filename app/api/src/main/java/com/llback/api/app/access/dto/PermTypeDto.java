package com.llback.api.app.access.dto;

import com.llback.api.app.permission.dto.PermDto;
import lombok.Data;

import java.util.List;

/**
 * 权限类型DTO
 */
@Data
public class PermTypeDto {
    /**
     * 权限类型
     */
    private String type;

    /**
     * 权限列表
     */
    private List<PermDto> perms;
}
