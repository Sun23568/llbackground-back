package com.llback.dal.perm.po;

import lombok.Data;

import java.util.List;

/**
 * 权限类型-权限
 */
@Data
public class PermissionTypePo {
    /**
     * 权限类型
     */
    private String type;

    /**
     * 权限
     */
    private List<PermissionPo> perms;
}
