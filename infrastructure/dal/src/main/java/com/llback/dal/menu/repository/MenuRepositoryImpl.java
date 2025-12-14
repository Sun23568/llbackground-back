package com.llback.dal.menu.repository;

import com.llback.common.types.UserId;
import com.llback.core.menu.eo.MenuEo;
import com.llback.core.menu.repository.MenuRepository;
import com.llback.dal.menu.dao.MenuDao;
import com.llback.dal.menu.po.MenuPo;
import com.llback.rt.common.util.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 菜单数据访问接口实现
 */
@Component
public class MenuRepositoryImpl implements MenuRepository {
    /**
     * 菜单数据访问接口
     */
    @Autowired
    private MenuDao menuDao;

    /**
     * 查询用户菜单
     */
    @Override
    public List<MenuEo> queryUserMenus(UserId userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        List<MenuPo> menuPoList = menuDao.queryUserMenus(userId.toString());
        return PoAssembleUtil.poList2EoList(menuPoList, MenuEo.class);
    }

    /**
     * 获取所有菜单
     */
    @Override
    public List<MenuEo> getAllMenus() {
        List<MenuPo> menuPoList = menuDao.queryAllMenus();
        return PoAssembleUtil.poList2EoList(menuPoList, MenuEo.class);
    }

    /**
     * 添加菜单
     */
    @Override
    public void addMenu(MenuEo menuEo) {
        MenuPo menuPo = PoAssembleUtil.eo2Po(menuEo, MenuPo.class);
        menuDao.addMenu(menuPo);
    }

    /**
     * 更新菜单
     */
    @Override
    public void updateMenu(MenuEo menuEo) {
        MenuPo po = PoAssembleUtil.eo2Po(menuEo, MenuPo.class);
        menuDao.updateMenu(po);
    }

    /**
     * 删除菜单（含级联删除用户关联）
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteMenu(String menuId) {
        // 1. 先删除用户菜单关联
        menuDao.deleteUserMenus(menuId);
        // 2. 再删除菜单本身
        menuDao.deleteMenu(menuId);
    }

    /**
     * 分页查询菜单列表
     */
    @Override
    public com.github.pagehelper.PageInfo<MenuEo> queryMenuPage(int pageNum, int pageRow) {
        com.github.pagehelper.PageHelper.startPage(pageNum, pageRow);
        com.github.pagehelper.PageInfo<MenuPo> pageInfo = new com.github.pagehelper.PageInfo<>(menuDao.queryMenuPage());
        return PoAssembleUtil.poPage2EoPage(pageInfo, MenuEo.class);
    }
}
