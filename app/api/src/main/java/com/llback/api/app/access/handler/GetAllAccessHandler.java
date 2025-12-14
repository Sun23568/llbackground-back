package com.llback.api.app.access.handler;

import com.llback.api.app.access.dto.PermTypeDto;
import com.llback.api.app.access.dto.req.AccessAllPermReq;
import com.llback.api.app.access.dto.resp.AccessAllPermResp;
import com.llback.api.app.menu.dto.MenuDto;
import com.llback.api.app.permission.dto.PermDto;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.core.menu.eo.MenuEo;
import com.llback.core.menu.repository.MenuRepository;
import com.llback.core.perm.eo.FuncPermEo;
import com.llback.core.perm.repository.PermRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 获取所有权限处理器
 */
@HandlerAcl("access:getAll")
@Component
public class GetAllAccessHandler implements Handler<AccessAllPermResp, AccessAllPermReq> {
    /**
     * 权限仓库
     */
    @Autowired
    private PermRepository permRepository;

    /**
     * 菜单仓库
     */
    @Autowired
    private MenuRepository menuRepository;

    /**
     * 获取所有权限
     */
    @Override
    public AccessAllPermResp execute(AccessAllPermReq req) {
        AccessAllPermResp accessAllPermResp = new AccessAllPermResp();

        // 获取所有权限
        List<FuncPermEo> allPermission = permRepository.getAllPermission();
        // 转为权限DTO并按照类型分组
        List<PermDto> permDtos = DtoEoAssemblerUtil.eoList2DtoList(allPermission, PermDto.class);
        List<PermTypeDto> permTypes = permDtos.stream()
                .collect(Collectors.groupingBy(PermDto::getPermType))
                .values().stream().map(item -> {
                    PermTypeDto permTypeDto = new PermTypeDto();
                    permTypeDto.setType(item.get(0).getPermType());
                    permTypeDto.setPerms(item);
                    return permTypeDto;
                }).collect(Collectors.toList());
        accessAllPermResp.setPermTypes(permTypes);

        // 获取所有菜单
        List<MenuEo> allMenu = menuRepository.getAllMenus();
        List<MenuDto> menuDtoList = DtoEoAssemblerUtil.eoList2DtoList(allMenu, MenuDto.class);
        accessAllPermResp.setMenus(menuDtoList);

        return accessAllPermResp;
    }
}
