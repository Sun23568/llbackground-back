package com.llback.core.user.eo;

import lombok.Builder;
import lombok.Getter;

/**
 * 功能权限
 */
@Getter
@Builder
public class FuncPermEo {
    /**
     * 功能权限ID
     */
    private String id;

    /**
     * 功能权限编码
     */
    private String permissionCode;

    /**
     * 功能权限名称
     */
    private String permissionName;

}
