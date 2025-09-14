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
}
