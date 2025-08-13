package com.llback.api.app.access.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

/**
 * 权限列表查询
 */
@Data
public class AccessListReq implements Query {
    public static AccessListReq EMPTY = new AccessListReq();
}
