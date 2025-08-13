package com.llback.dal.user.repository;

import com.llback.api.app.access.dto.UserAccessDto;
import com.llback.api.app.access.fetch.AccessFetch;
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
}
