package com.llback.api.api;

import com.llback.api.app.user.dto.req.*;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问控制
 */
@RestController
@RequestMapping("/user")
public class UserApi implements RestApi {
    /**
     * 登录
     */
    @RequestMapping("/list")
    public RestResult queryUserList(int pageNum, int pageRow) {
        return this.execute(QueryUserListReq.of(pageNum, pageRow));
    }

    /**
     * 添加用户
     */
    @RequestMapping("/add")
    public RestResult addUser(@RequestBody AddUserCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 修改用户
     */
    @RequestMapping("/update")
    public RestResult updateUser(@RequestBody UpdateUserCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 修改当前用户
     */
    @RequestMapping("/update/cur")
    public RestResult updateCurUser(@RequestBody UpdateCurUserCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 删除用户
     */
    @RequestMapping("/remove")
    public RestResult removeUser(@RequestBody RemoveUsersCmd cmd) {
        return this.execute(cmd);
    }
}
