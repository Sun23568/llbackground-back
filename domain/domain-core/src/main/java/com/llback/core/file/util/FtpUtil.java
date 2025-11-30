package com.llback.core.file.util;

import com.llback.common.util.AssertUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
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

    private static FtpUtil instance;

    /**
     * FTP配置属性类
     */
    @Autowired
    private FtpProperties ftpProperties;

    @PostConstruct
    public void init() {
        instance = this;
    }

    /**
     * 上传文件至服务器
     *
     * @author yz.sun
     * @date 2025/9/10
     */
    public static String uploadFile(String filePath, String uuid, InputStream fileInputStream) throws IOException {
        FTPClient ftpClient = FtpUtil.getFtpClient();
        try {
            // 如果filePath文件夹不存在，则创建
            if (!FtpUtil.isDirectory(ftpClient, filePath)) {
                ftpClient.makeDirectory(filePath);
            }
            ftpClient.changeWorkingDirectory(filePath);
            ftpClient.appendFile(uuid, fileInputStream);
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
        return instance.ftpProperties.getPath() + filePath;
    }

    /**
     * 获取FTP客户端
     *
     * @throws IOException
     */
    private static FTPClient getFtpClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(instance.ftpProperties.getIp(), instance.ftpProperties.getPort());
        ftpClient.login(instance.ftpProperties.getUser(), instance.ftpProperties.getPassword());
        ftpClient.enterLocalPassiveMode();
        // 设置文件类型为二进制，确保文件传输完整性
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.changeWorkingDirectory(instance.ftpProperties.getPath());
        ftpClient.setConnectTimeout(5000);
        return ftpClient;
    }

    /**
     * 判断FTP服务器上的路径是否是目录
     *
     * @param ftpClient FTP客户端
     * @param path      要检查的路径
     * @return true表示是目录，false表示不是目录
     * @throws IOException IO异常
     */
    private static boolean isDirectory(FTPClient ftpClient, String path) throws IOException {
        // 尝试直接获取该路径的信息
        FTPFile[] files = ftpClient.listFiles(path);

        // 如果能列出文件，则说明路径存在
        // 进一步判断：如果返回的文件列表中第一个文件的名称与路径末尾名称一致，且是目录
        if (files.length > 0) {
            FTPFile file = files[0];
            String pathEnd = path.substring(path.lastIndexOf("/") + 1);

            // 如果名称匹配且为目录
            if (file.getName().equals(pathEnd) && file.isDirectory()) {
                return true;
            }
        }

        // 备用方案：尝试切换工作目录
        String originalDir = ftpClient.printWorkingDirectory();
        try {
            boolean success = ftpClient.changeWorkingDirectory(path);
            if (success) {
                ftpClient.changeWorkingDirectory(originalDir);
                return true;
            }
        } catch (IOException e) {
            // 忽略异常，返回false
        }

        return false;
    }

    /**
     * 获取文件
     *
     * @param fileName
     * @param path
     */
    public static byte[] getFile(String fileName, String path) throws IOException {
        FTPClient ftpClient = getFtpClient();
        try {
            // 如果filePath文件夹不存在，则创建
            if (!isDirectory(ftpClient, path)) {
                return null;
            }
            ftpClient.changeWorkingDirectory(path);
            // 获取文件输入流
            InputStream inputStream = ftpClient.retrieveFileStream(fileName);

            AssertUtil.notNull(inputStream, "文件不存在：" + fileName);

            // 将流复制到字节数组
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            buffer.flush();

            // 完成FTP操作
            ftpClient.completePendingCommand();

            return buffer.toByteArray();
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    /**
     * 删除FTP服务器上的文件
     *
     * @param fileName 文件名
     * @param path     文件路径
     * @return true表示删除成功，false表示删除失败
     * @throws IOException IO异常
     */
    public static boolean deleteFile(String fileName, String path) throws IOException {
        FTPClient ftpClient = getFtpClient();
        try {
            // 检查路径是否存在
            if (!isDirectory(ftpClient, path)) {
                return false;
            }
            // 切换到目标目录
            ftpClient.changeWorkingDirectory(path);
            // 删除文件
            return ftpClient.deleteFile(fileName);
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}
