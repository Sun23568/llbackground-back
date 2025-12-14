package com.llback.dal.menu.repository;

import com.llback.api.app.menu.dto.MenuDto;
import com.llback.api.app.menu.fetch.MenuFetch;
import com.llback.dal.menu.dao.MenuDao;
import com.llback.dal.menu.po.MenuPo;
import com.llback.rt.common.util.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 菜单数据获取实现
 */
@Component
public class MenuFetchImpl implements MenuFetch {
    /**
     * 菜单Dao
     */
    @Autowired
    private MenuDao menuDao;

    /**
     * 根据菜单代码查询菜单
     */
    @Override
    public MenuDto queryMenuByCode(String menuCode) {
        MenuPo menuPo = menuDao.queryMenuByCode(menuCode);
        return PoAssembleUtil.poToDto(menuPo, MenuDto.class);
    }

    /**
     * 获取菜单编码数量
     */
    @Override
    public int getMenuCodeCount(String menuCode) {
        return menuDao.getMenuCodeCount(menuCode);
    }
}
