package com.llback.api.api;

import com.llback.api.app.ai.dto.req.UploadBackgroundReq;
import com.llback.api.app.sa.dto.req.*;
import com.llback.api.app.sa.dto.resp.LoginResp;
import com.llback.api.app.sa.dto.resp.PublicKeyResp;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 鉴权接口
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@RestController
@RequestMapping("/ai")
public class AiApi implements RestApi {

    /**
     * 登录
     */
    @RequestMapping("/upload/background")
    public RestResult<LoginResp> uploadBackground(@RequestParam("file") MultipartFile file, @RequestParam("aiMenuId") String aiMenuId) {
        return this.execute(UploadBackgroundReq.of(file, aiMenuId));
    }
}
