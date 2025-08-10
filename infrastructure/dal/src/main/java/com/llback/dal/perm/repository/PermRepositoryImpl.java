package com.llback.dal.perm.repository;

import com.llback.common.types.UserId;
import com.llback.core.perm.eo.FuncPermEo;
import com.llback.core.perm.repository.PermRepository;
import com.llback.dal.perm.dao.PermDao;
import com.llback.dal.perm.po.PermissionPo;
import com.llback.rt.common.cache.AssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限仓库实现
 */
@Component
public class PermRepositoryImpl implements PermRepository {
    /**
     * 权限数据访问接口
     */
    @Autowired
    private PermDao permDao;

    /**
     * 查询用户权限
     */
    @Override
    public List<FuncPermEo> queryUserPerms(UserId userId) {
        List<PermissionPo> permissionPoList = permDao.queryUserPerms(userId.toString());
        return AssembleUtil.poList2EoList(permissionPoList, FuncPermEo.class);
    }
}
