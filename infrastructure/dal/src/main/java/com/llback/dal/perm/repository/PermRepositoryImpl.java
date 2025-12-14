package com.llback.dal.perm.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.llback.common.types.UserId;
import com.llback.core.perm.eo.FuncPermEo;
import com.llback.core.perm.repository.PermRepository;
import com.llback.dal.perm.dao.PermDao;
import com.llback.dal.perm.po.PermissionPo;
import com.llback.rt.common.util.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        return PoAssembleUtil.poList2EoList(permissionPoList, FuncPermEo.class);
    }

    /**
     * 获取所有权限
     */
    @Override
    public List<FuncPermEo> getAllPermission() {
        List<PermissionPo> permissionPos = permDao.queryAllPermission();
        return PoAssembleUtil.poList2EoList(permissionPos, FuncPermEo.class);
    }

    /**
     * 添加权限
     */
    @Override
    public void addPerm(FuncPermEo permEo) {
        PermissionPo permissionPo = PoAssembleUtil.eo2Po(permEo, PermissionPo.class);
        permDao.addPermission(permissionPo);
    }

    /**
     * 更新权限
     */
    @Override
    public void updatePerm(FuncPermEo permEo) {
        PermissionPo po = PoAssembleUtil.eo2Po(permEo, PermissionPo.class);
        permDao.updatePermission(po);
    }

    /**
     * 删除权限（含级联删除用户关联）
     */
    @Override
    @Transactional
    public void deletePerm(String permId) {
        // 1. 先删除用户权限关联
        permDao.deleteUserPermissions(permId);
        // 2. 再删除权限本身
        permDao.deletePermission(permId);
    }

    /**
     * 分页查询权限列表
     */
    @Override
    public PageInfo<FuncPermEo> queryPermPage(int pageNum, int pageRow, String permType) {
        PageHelper.startPage(pageNum, pageRow);
        PageInfo<PermissionPo> pageInfo = new PageInfo<>(permDao.queryPermPage(permType));
        return PoAssembleUtil.poPage2EoPage(pageInfo, FuncPermEo.class);
    }
}
