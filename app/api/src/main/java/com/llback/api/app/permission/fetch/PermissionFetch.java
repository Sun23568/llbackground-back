package com.llback.api.app.permission.fetch;

/**
 * 权限数据获取接口
 */
public interface PermissionFetch {
    /**
     * 获取权限编码数量
     *
     * @param permCode 权限编码
     * @return 数量
     */
    int getPermCodeCount(String permCode);
}
