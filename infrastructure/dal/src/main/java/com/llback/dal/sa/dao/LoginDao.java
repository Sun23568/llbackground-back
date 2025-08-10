package com.llback.dal.sa.dao;

import com.llback.dal.menu.po.MenuPo;
import com.llback.dal.perm.po.PermissionPo;

import java.util.List;

/**
 * @author: heeexy
 * @description: 登录相关dao
 * @date: 2017/10/24 11:02
 */
public interface LoginDao {
    List<MenuPo> getAllMenu();

    List<PermissionPo> getAllPermissionCode();
}
