package com.llback.rt.common.cache;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.net.ftp.FTP;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件工具类
 *
 * @author yz.sun
 * @date 2025/9/10
 */
@Component
public class FtpUtil {
    /**
     * ftp配置
     */
    @Autowired
    private FtpProperties ftpProperties;

    /**
     * 上传文件至服务器
     *
     * @author yz.sun
     * @date 2025/9/10
     */
    public String uploadFile(String filePath, String uuid, InputStream fileInputStream) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpProperties.getIp(), ftpProperties.getPort());
        ftpClient.login(ftpProperties.getUser(), ftpProperties.getPassword());
        ftpClient.enterLocalPassiveMode();
        // 设置文件类型为二进制，确保文件传输完整性
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.changeWorkingDirectory(ftpProperties.getPath());
        ftpClient.setConnectTimeout(5000);
        // 如果filePath文件夹不存在，则创建
        if (!isDirectory(ftpClient, filePath)) {
            ftpClient.makeDirectory(filePath);
        }
        ftpClient.changeWorkingDirectory(filePath);
        ftpClient.appendFile(uuid, fileInputStream);
        ftpClient.logout();
        ftpClient.disconnect();
        return ftpProperties.getPath() + filePath;
    }

    /**
     * 判断FTP服务器上的路径是否是目录
     *
     * @param ftpClient FTP客户端
     * @param path 要检查的路径
     * @return true表示是目录，false表示不是目录
     * @throws IOException IO异常
     */
    public boolean isDirectory(FTPClient ftpClient, String path) throws IOException {
        // 获取文件状态信息
        FTPFile[] files = ftpClient.listFiles(path);

        if (files.length > 0) {
            // 检查第一个文件的类型
            return files[0].isDirectory();
        }

        return false;
    }
}
