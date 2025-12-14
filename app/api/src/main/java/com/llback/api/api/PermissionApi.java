package com.llback.api.api;

import com.llback.api.app.permission.dto.req.*;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.*;

/**
 * 权限管理
 */
@RestController
@RequestMapping("/permission")
public class PermissionApi implements RestApi {
    /**
     * 添加权限
     */
    @PostMapping("/add")
    public RestResult addPermission(@RequestBody AddPermCmd addPermCmd) {
        this.execute(addPermCmd);
        return RestResult.ok(null);
    }

    /**
     * 权限分页查询
     */
    @GetMapping("/query")
    public RestResult queryPermission(int pageNum, int pageRow, QueryPermissionReq queryPermissionReq) {
        return this.execute(queryPermissionReq.setPageQueryDto(pageNum, pageRow));
    }

    /**
     * 查询权限类型列表
     */
    @GetMapping("/types")
    public RestResult queryPermTypes() {
        return this.execute(QueryPermTypesReq.EMPTY);
    }

    /**
     * 更新权限
     */
    @PostMapping("/update")
    public RestResult updatePermission(@RequestBody UpdatePermCmd updatePermCmd) {
        this.execute(updatePermCmd);
        return RestResult.ok(null);
    }

    /**
     * 删除权限
     */
    @PostMapping("/remove")
    public RestResult removePermission(@RequestBody RemovePermCmd removePermCmd) {
        this.execute(removePermCmd);
        return RestResult.ok(null);
    }
}
