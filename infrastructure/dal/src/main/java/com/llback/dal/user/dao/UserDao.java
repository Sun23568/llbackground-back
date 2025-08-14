package com.llback.dal.user.dao;


import com.alibaba.fastjson2.JSONObject;
import com.llback.dal.user.po.UserMenuPo;
import com.llback.dal.user.po.UserPermPo;
import com.llback.dal.user.po.UserPo;

import java.util.List;

/**
 * @author: heeexy
 * @description: 用户/角色/权限
 * @date: 2017-11-14 15:08:45
 */
public interface UserDao {
    /**
     * 根据用户名和密码查询对应的用户
     */
    UserPo checkUser(String username, String password);

    /**
     * 根据用户id查询用户信息
     */
    UserPo getUserInfo(String userId);

    /**
     * 查询用户数量
     */
    int countUser(JSONObject jsonObject);

    /**
     * 查询用户列表
     */
    List<JSONObject> listUser(JSONObject jsonObject);

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    List<JSONObject> getAllRoles();

    /**
     * 校验用户名是否已存在
     */
    int queryExistUsername(JSONObject jsonObject);

    /**
     * 新增用户
     */
    int addUser(JSONObject jsonObject);

    /**
     * 修改用户
     */
    int updateUser(JSONObject jsonObject);

    int batchAddUserRole(JSONObject jsonObject);

    int removeUserAllRole(int userId);

    /**
     * 角色列表
     */
    List<JSONObject> listRole();

    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    List<JSONObject> listAllPermission();

    /**
     * 新增角色
     */
    int insertRole(JSONObject jsonObject);

    /**
     * 批量插入角色的权限
     *
     * @param roleId      角色ID
     * @param permissions 权限
     */
    int insertRolePermission(String roleId, List<Integer> permissions);

    /**
     * 将角色曾经拥有而修改为不再拥有的权限 delete_status改为'2'
     */
    int removeOldPermission(String roleId, List<Integer> permissions);

    /**
     * 修改角色名称
     */
    int updateRoleName(JSONObject jsonObject);

    /**
     * 查询某角色的全部数据
     * 在删除和修改角色时调用
     */
    JSONObject getRoleAllInfo(JSONObject jsonObject);

    /**
     * 统计角色下有多少个有效用户
     */
    int countRoleUser(String roleId);

    /**
     * 删除角色
     */
    int removeRole(String roleId);

    /**
     * 删除本角色全部权限
     */
    int removeRoleAllPermission(String roleId);

    /**
     * 查询所有用户权限
     */
    List<UserPermPo> queryAllUserPerms();

    /**
     * 查询所有用户菜单
     */
    List<UserMenuPo> queryAllUserMenus();

    /**
     * 删除用户权限
     */
    void removeUserPerms(String userId);

    /**
     * 删除用户菜单
     */
    void removeUserMenus(String userId);

    /**
     * 添加用户权限
     */
    void addUserPerms(String userId, List<String> permissions);

    /**
     * 添加用户菜单
     */
    void addUserMenus(String userId, List<String> menuIds);
}
