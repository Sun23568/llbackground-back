
package com.llback.dal.perm.po;

import lombok.Data;

/**
 * 权限持久化对象
 */
@Data
public class PermissionPo {
    /**
     * 权限ID
     */
    private String permId;

    /**
     * 权限代码
     */
    private String permCode;
    
    /**
     * 权限名称
     */
    private String permName;

    /**
     * 权限类型
     * 作为分组依据
     */
    private String type;
}
