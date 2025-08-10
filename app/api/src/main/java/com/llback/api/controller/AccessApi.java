package com.llback.api.controller;

import com.llback.api.dto.access.req.AccessAllReq;
import com.llback.api.dto.access.resp.AccessAllResp;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问控制
 */
@RestController
@RequestMapping("/access")
public class AccessApi implements RestApi {
    /**
     * 查询所有权限、菜单
     */
    @GetMapping("/all")
    public RestResult<AccessAllResp> listAll() {
        return this.execute(AccessAllReq.EMPTY);
    }
}
