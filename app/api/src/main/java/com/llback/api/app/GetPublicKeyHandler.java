package com.llback.api.app;

import com.llback.api.dto.sa.req.GetPublicKeyReq;
import com.llback.api.dto.sa.req.GetSessionReq;
import com.llback.api.dto.sa.resp.PublicKeyResp;
import com.llback.api.dto.sa.resp.UserSessionResp;
import com.llback.frame.Handler;
import com.llback.frame.PubAcl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
