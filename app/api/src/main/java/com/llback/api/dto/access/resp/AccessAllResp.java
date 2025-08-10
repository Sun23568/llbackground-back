package com.llback.api.dto.access.resp;

import com.llback.api.dto.access.MenuDto;
import com.llback.api.dto.access.PermDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 获取所有权限返回
 */
@Data
public class AccessAllResp {
    /**
     * 权限列表
     */
    private Map<String, List<PermDto>> permDtosTypeMap;

    /**
     * 菜单列表
     */
    private List<MenuDto> menuDtos;
}
