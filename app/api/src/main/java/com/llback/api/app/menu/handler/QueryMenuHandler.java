package com.llback.api.app.menu.handler;

import com.github.pagehelper.PageInfo;
import com.llback.api.app.menu.dto.MenuDto;
import com.llback.api.app.menu.dto.req.QueryMenuReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.core.menu.eo.MenuEo;
import com.llback.core.menu.repository.MenuRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import com.llback.frame.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 菜单分页查询处理器
 */
@Component
@HandlerAcl("menu:query")
public class QueryMenuHandler implements Handler<PageResult<MenuDto>, QueryMenuReq> {
    /**
     * 菜单仓储接口
     */
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public PageResult<MenuDto> execute(QueryMenuReq req) {
        PageInfo<MenuEo> menuEoPageInfo = menuRepository.queryMenuPage(req.getPageIndex(), req.getPageSize());
        return DtoEoAssemblerUtil.eoPageToDtoResult(menuEoPageInfo, MenuDto.class);
    }
}
