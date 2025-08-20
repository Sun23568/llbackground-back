package com.llback.dal.user.repository;

import com.llback.api.app.access.dto.UserAccessDto;
import com.llback.api.app.access.fetch.AccessFetch;
import com.llback.dal.menu.dao.MenuDao;
import com.llback.dal.perm.dao.PermDao;
import com.llback.dal.user.assembler.AccessAssembler;
import com.llback.dal.user.dao.UserDao;
import com.llback.dal.user.po.UserMenuPo;
import com.llback.dal.user.po.UserPermPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccessFetchImpl implements AccessFetch {

    /**
     * 用户Dao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 权限Dao
     */
    @Autowired
    private PermDao permDao;

    /**
     * 菜单Dao
     */
    @Autowired
    private MenuDao menuDao;

    /**
     * 查询所有用户权限
     */
    @Override
    public List<UserAccessDto> queryAllUserPerms() {
        List<UserPermPo> userPermPoList = userDao.queryAllUserPerms();
        return AccessAssembler.userPermPoToDto(userPermPoList);
    }

    /**
     * 查询所有用户菜单
     */
    @Override
    public List<UserAccessDto> queryAllUserMenus() {
        List<UserMenuPo> userMenuPos = userDao.queryAllUserMenus();
        return AccessAssembler.userMenuPoToDto(userMenuPos);
    }

    /**
     * 删除所有用户权限
     */
    @Override
    public void removeUserPerms(String userId) {
        userDao.removeUserPerms(userId);
    }

    /**
     * 删除用户菜单
     */
    @Override
    public void removeUserMenus(String userId) {
        userDao.removeUserMenus(userId);
    }

    /**
     * 添加用户权限
     */
    @Override
    public void addUserPerms(String userId, List<String> permissions) {
        userDao.addUserPerms(userId, permissions);
    }

    /**
     * 添加用户菜单
     */
    @Override
    public void addUserMenus(String userId, List<String> menuIds) {
        userDao.addUserMenus(userId, menuIds);
    }

    /**
     * 查询权限码数量
     */
    @Override
    public int getPermCodeCount(String permCode) {
        return permDao.getPermCodeCount(permCode);
    }

    /**
     * 查询菜单码数量
     */
    @Override
    public int getMenuCodeCount(String menuCode) {
        return menuDao.getMenuCodeCount(menuCode);
    }
}
