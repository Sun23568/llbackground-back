package com.llback.api.dto.sa.resp;

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
