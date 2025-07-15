package com.llback.core.sa.eo;

import lombok.*;

/**
 * 用户EO
 */
@Getter
@Builder
@ToString
public class UserEo {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;
}
