package com.llback.dal.perm.repository;

import com.llback.api.app.permission.fetch.PermissionFetch;
import com.llback.dal.perm.dao.PermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 权限数据获取实现
 */
@Component
public class PermissionFetchImpl implements PermissionFetch {
    /**
     * 权限Dao
     */
    @Autowired
    private PermDao permDao;

    /**
     * 获取权限编码数量
     */
    @Override
    public int getPermCodeCount(String permCode) {
        return permDao.getPermCodeCount(permCode);
    }
}
