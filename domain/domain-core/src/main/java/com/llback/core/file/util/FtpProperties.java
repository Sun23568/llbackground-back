package com.llback.core.file.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * FTP配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {

    /**
     * FTP服务器IP地址
     */
    private String ip;

    /**
     * FTP服务器端口
     */
    private int port;

    /**
     * FTP用户
     */
    private String user;

    /**
     * FTP密码
     */
    private String password;

    /**
     * FTP路径
     */
    private String path;
}
