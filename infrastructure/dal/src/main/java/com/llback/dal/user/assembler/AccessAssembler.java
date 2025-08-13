package com.llback.dal.user.assembler;

import com.llback.api.app.access.dto.MenuDto;
import com.llback.api.app.access.dto.PermDto;
import com.llback.api.app.access.dto.PermTypeDto;
import com.llback.api.app.access.dto.UserAccessDto;
import com.llback.dal.menu.po.MenuPo;
import com.llback.dal.perm.po.PermissionPo;
import com.llback.dal.perm.po.PermissionTypePo;
import com.llback.dal.user.po.UserMenuPo;
import com.llback.dal.user.po.UserPermPo;

import java.util.ArrayList;
import java.util.List;

public class AccessAssembler {

    /**
     * poList 转 dto
     *
     * @param poList
     * @return
     */
    public static List<UserAccessDto> userPermPoToDto(List<UserPermPo> poList) {
        List<UserAccessDto> dtoList = new ArrayList<>();
        for (UserPermPo po : poList) {
            UserAccessDto dto = new UserAccessDto();
            dto.setUserId(po.getUserId());
            dto.setUserName(po.getUserName());
            List<PermTypeDto> permTypeDtos = new ArrayList<>();
            for (PermissionTypePo permTypePo : po.getPermTypes()) {
                PermTypeDto permTypeDto = new PermTypeDto();
                permTypeDto.setType(permTypePo.getType());
                List<PermissionPo> perms = permTypePo.getPerms();
                List<PermDto> permDtos = new ArrayList<>();
                for (PermissionPo perm : perms) {
                    PermDto permDto = new PermDto();
                    permDto.setPermId(perm.getPermId());
                    permDto.setPermCode(perm.getPermCode());
                    permDto.setPermName(perm.getPermName());
                    permDto.setType(perm.getType());
                    permDtos.add(permDto);
                }
                permTypeDto.setPerms(permDtos);
                permTypeDtos.add(permTypeDto);
            }
            dto.setPermTypes(permTypeDtos);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 菜单权限转换
     *
     * @param poList
     * @return
     */
    public static List<UserAccessDto> userMenuPoToDto(List<UserMenuPo> poList) {
        List<UserAccessDto> dtoList = new ArrayList<>();
        for (UserMenuPo po : poList) {
            UserAccessDto dto = new UserAccessDto();
            dto.setUserId(po.getUserId());
            dto.setUserName(po.getUserName());
            List<MenuDto> menuDtos = new ArrayList<>();
            for (MenuPo menu : po.getMenus()) {
                MenuDto menuDto = new MenuDto();
                menuDto.setMenuId(menu.getMenuId());
                menuDto.setMenuCode(menu.getMenuCode());
                menuDto.setMenuName(menu.getMenuName());
                menuDtos.add(menuDto);
            }
            dto.setMenus(menuDtos);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
