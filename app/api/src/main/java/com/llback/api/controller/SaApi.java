package com.llback.api.controller;

import com.llback.api.dto.sa.req.GetPublicKeyReq;
import com.llback.api.dto.sa.req.GetSessionReq;
import com.llback.api.dto.sa.req.LoginCmd;
import com.llback.api.dto.sa.resp.LoginResp;
import com.llback.api.dto.sa.resp.PublicKeyResp;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权接口
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@RestController
@RequestMapping("/sa")
public class SaApi implements RestApi {

    /**
     * 登录
     *
     * @param cmd
     * @return
     */
    @RequestMapping("/login")
    public RestResult<LoginResp> login(@RequestBody LoginCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 获取登录信息
     * 根据token
     */
    @RequestMapping("/session")
    public RestResult<LoginResp> getInfo() {
        return this.execute(GetSessionReq.EMPTY);
    }

    @RequestMapping("/publicKey")
    public RestResult<PublicKeyResp> getPublicKey() {
        return this.execute(GetPublicKeyReq.EMPTY);
    }
}
