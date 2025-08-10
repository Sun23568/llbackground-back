package com.llback.api.app;

import com.llback.api.dto.access.MenuDto;
import com.llback.api.dto.access.PermDto;
import com.llback.api.dto.access.req.AccessAllReq;
import com.llback.api.dto.access.resp.AccessAllResp;
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
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 获取所有权限处理器
 */
@HandlerAcl("access:getAll")
@Component
public class GetAllAccessHandler implements Handler<AccessAllResp, AccessAllReq> {
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
    public AccessAllResp execute(AccessAllReq req) {
        AccessAllResp accessAllResp = new AccessAllResp();

        // 获取所有权限
        List<FuncPermEo> allPermission = permRepository.getAllPermission();
        // 转为权限DTO并按照类型分组
        List<PermDto> permDtos = DtoEoAssemblerUtil.eoList2DtoList(allPermission, PermDto.class);
        Map<String, List<PermDto>> permGroupByType = permDtos.stream().collect(Collectors.groupingBy(PermDto::getType));
        accessAllResp.setPermDtosTypeMap(permGroupByType);

        // 获取所有菜单
        List<MenuEo> allMenu = menuRepository.getAllMenus();
        List<MenuDto> menuDtoList = DtoEoAssemblerUtil.eoList2DtoList(allMenu, MenuDto.class);
        accessAllResp.setMenuDtos(menuDtoList);

        return accessAllResp;
    }
}
