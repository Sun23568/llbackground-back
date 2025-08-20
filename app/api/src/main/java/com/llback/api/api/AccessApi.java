package com.llback.api.api;

import com.llback.api.app.access.dto.req.*;
import com.llback.api.app.access.dto.resp.AccessAllPermResp;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.*;

/**
 * 访问控制
 */
@RestController
@RequestMapping("/access")
public class AccessApi implements RestApi {
    /**
     * 查询所有权限、菜单
     */
    @GetMapping("/all-access")
    public RestResult<AccessAllPermResp> listAll() {
        return this.execute(AccessAllPermReq.EMPTY);
    }

    /**
     * 人员配置权限列表查询
     */
    @GetMapping("/list")
    public RestResult<AccessAllPermResp> list() {
        return this.execute(AccessListReq.EMPTY);
    }

    /**
     * 更新用户权限
     */
    @PostMapping("/update-user")
    public RestResult updateUserAccess(@RequestBody UpdateUserAccessCmd updateUserAccessCmd) {
        this.execute(updateUserAccessCmd);
        return RestResult.ok(null);
    }

    /**
     * 新增权限
     */
    @PostMapping("/add-perm")
    public RestResult addPerm(@RequestBody AddPermCmd addPermCmd) {
        this.execute(addPermCmd);
        return RestResult.ok(null);
    }

    @PostMapping("/add-menu")
    public RestResult addMenu(@RequestBody AddMenuCmd addMenuCmd) {
        this.execute(addMenuCmd);
        return RestResult.ok(null);
    }
}
