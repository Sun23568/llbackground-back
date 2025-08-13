package com.llback.api.app.access.dto.resp;

import com.llback.api.app.access.dto.UserAccessDto;
import lombok.Data;

import java.util.List;

/**
 * 权限列表返回DTO
 */
@Data
public class AccessListResp {
    /**
     * 用户权限列表
     */
    private List<UserAccessDto> userAccessRespList;
}
