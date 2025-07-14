package com.llback.dal.sa.dao;

import com.llback.dal.sa.po.MenuPo;
import com.llback.dal.sa.po.PermissionPo;
import com.llback.dal.sa.po.UserPo;

import java.util.List;
import java.util.Set;

/**
 * @author: heeexy
 * @description: 登录相关dao
 * @date: 2017/10/24 11:02
 */
public interface LoginDao {
    /**
     * 根据用户名和密码查询对应的用户
     */
    UserPo checkUser(String username, String password);

    UserPo getUserInfo(String username);

    List<MenuPo> getAllMenu();

    List<PermissionPo> getAllPermissionCode();
}
