package com.llback.api.app.user.dto.resp;

import com.llback.api.app.user.dto.UserDto;
import lombok.Data;

import java.util.List;

/**
 * 查询用户列表响应
 */
@Data
public class QueryUserListResp {
    /**
     * 用户列表
     */
    private List<UserDto> users;
}
