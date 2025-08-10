package com.llback.api.dto.access.req;

import com.llback.frame.dto.Query;
import lombok.Data;

/**
 * 获取所有权限请求
 */
@Data
public class AccessAllReq implements Query {
    public static AccessAllReq EMPTY = new AccessAllReq();
}
