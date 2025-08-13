package com.llback.api.app.access.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

/**
 * 获取所有权限请求
 */
@Data
public class AccessAllPermReq implements Query {
    public static AccessAllPermReq EMPTY = new AccessAllPermReq();
}
