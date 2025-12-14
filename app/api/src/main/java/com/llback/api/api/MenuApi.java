package com.llback.api.api;

import com.llback.api.app.menu.dto.req.AddMenuCmd;
import com.llback.api.app.menu.dto.req.QueryMenuReq;
import com.llback.api.app.menu.dto.req.RemoveMenuCmd;
import com.llback.api.app.menu.dto.req.UpdateMenuCmd;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单管理
 */
@RestController
@RequestMapping("/menu")
public class MenuApi implements RestApi {
    /**
     * 添加菜单
     */
    @PostMapping("/add")
    public RestResult addMenu(@RequestBody AddMenuCmd addMenuCmd) {
        this.execute(addMenuCmd);
        return RestResult.ok(null);
    }

    /**
     * 菜单分页查询
     */
    @GetMapping("/query")
    public RestResult queryMenu(int pageNum, int pageRow) {
        return this.execute(QueryMenuReq.of(pageNum, pageRow));
    }

    /**
     * 更新菜单
     */
    @PostMapping("/update")
    public RestResult updateMenu(@RequestBody UpdateMenuCmd updateMenuCmd) {
        this.execute(updateMenuCmd);
        return RestResult.ok(null);
    }

    /**
     * 删除菜单
     */
    @PostMapping("/remove")
    public RestResult removeMenu(@RequestBody RemoveMenuCmd removeMenuCmd) {
        this.execute(removeMenuCmd);
        return RestResult.ok(null);
    }
}
