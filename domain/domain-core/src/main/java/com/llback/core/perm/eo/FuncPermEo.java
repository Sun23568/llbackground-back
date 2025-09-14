package com.llback.core.perm.eo;

import com.llback.common.types.FuncCode;
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
    private String permId;

    /**
     * 功能权限编码
     */
    private FuncCode permCode;

    /**
     * 功能权限名称
     */
    private String permName;

    /**
     * 功能权限类型
     */
    private String type;

}
