package com.llback.dal.user.po;

import com.llback.dal.perm.po.PermissionTypePo;
import lombok.Data;

import java.util.List;

/**
 * 用户权限PO
 */
@Data
public class UserPermPo {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 权限列表
     */
    private List<PermissionTypePo> permTypes;
}
