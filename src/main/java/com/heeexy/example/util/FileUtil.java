package com.heeexy.example.util;

import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.net.SocketException;

/**
 * 文件工具类
 *
 * @author yz.sun
 * @date 2025/3/26
 */
public class FileUtil {

    @Value("${cloud.file-path}")
    private static String filePath;

    @Value("${cloud.user}")
    private static String user;

    @Value("${cloud.password}")
    private static String password;

    @Value("${cloud.ip}")
    private static String ip;

    public static void updateFileFtp(String filePath, byte[] bytes, String fileName) {
        FTPClient ftpClient = null;
        try {
            // 连接FTP服务器
            ftpClient = getConnection();
            // 不存在目录则创建
            if (!ftpClient.changeWorkingDirectory(filePath)) {
                String[] pathArray = filePath.split("/");
                StringBuilder path = new StringBuilder();
                for (String pathItem : pathArray) {
                    if (null == pathItem || "".equals(pathItem)) {
                        continue;
                    }
                    path.append("/").append(pathItem);
                    if (!ftpClient.changeWorkingDirectory(path.toString())) {
                        ftpClient.makeDirectory(path.toString());
                    }
                    ftpClient.changeWorkingDirectory(path.toString());
                }
            }
            // 上传文件
            ftpClient.storeFile(fileName, new java.io.ByteArrayInputStream(bytes));
            ftpClient.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取FTP连接
     *
     * @throws Exception
     * @author yz.sun
     * @date 2025/3/26
     */
    private static FTPClient getConnection() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(60 * 1000 * 6);
        ftpClient.setDataTimeout(60 * 1000 * 30);
        ftpClient.enterLocalPassiveMode();
        ftpClient.connect(ip, 21);
        ftpClient.login(user, password);
        ftpClient.setBufferSize(1024 * 1024 * 10);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        return ftpClient;
    }
}
