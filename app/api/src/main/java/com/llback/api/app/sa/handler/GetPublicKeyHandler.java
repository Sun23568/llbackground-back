package com.llback.api.app.sa.handler;

import com.llback.api.app.sa.dto.req.GetPublicKeyReq;
import com.llback.api.app.sa.dto.resp.PublicKeyResp;
import com.llback.frame.Handler;
import com.llback.frame.PubAcl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取用户会话信息
 */
@PubAcl
@Component
public class GetPublicKeyHandler implements Handler<PublicKeyResp, GetPublicKeyReq> {
    /**
     * 通过配置文件获取公钥
     */
    @Value("${sa.public-key}")
    private String publicKey;

    @Override
    public PublicKeyResp execute(GetPublicKeyReq req) {
        return new PublicKeyResp(publicKey);
    }
}
