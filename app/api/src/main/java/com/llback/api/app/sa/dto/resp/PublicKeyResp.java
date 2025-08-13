package com.llback.api.app.sa.dto.resp;

import lombok.Data;

/**
 * 公钥响应
 */
@Data
public class PublicKeyResp {
    /**
     * 公钥
     */
    private String publicKey;

    public PublicKeyResp(String publicKey) {
        this.publicKey = publicKey;
    }
}
