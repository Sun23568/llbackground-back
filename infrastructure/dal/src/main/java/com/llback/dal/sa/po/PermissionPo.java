
package com.llback.dal.sa.po;

import lombok.Data;

/**
 * 权限持久化对象
 */
@Data
public class PermissionPo {
    /**
     * 权限代码
     */
    private String permissionCode;
    
    /**
     * 权限名称
     */
    private String permissionName;
}
