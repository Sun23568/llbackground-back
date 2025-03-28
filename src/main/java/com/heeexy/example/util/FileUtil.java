package com.heeexy.example.util;

import com.heeexy.example.dao.FileDao;
import com.heeexy.example.dto.resp.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 文件工具类
 *
 * @author yz.sun
 * @date 2025/3/26
 */
@Slf4j
@Component
public class FileUtil {

    @Value("${cloud.file-path}")
    private String filePath;

    @Value("${cloud.user}")
    private String user;

    @Value("${cloud.password}")
    private String password;

    @Value("${cloud.ip}")
    private String ip;

    @Autowired
    private FileDao fileDao;

    /**
     * 根据文件ID获取文件
     *
     * @author yz.sun
     * @date 2025/3/27
     */
    public byte[] getFileById(String fileId) {
        // 获取持久化文件信息
        FileInfo fileInfo = fileDao.getFileById(fileId);
        AssertUtils.assertNotNull(fileInfo, "未找到对应的文件信息");
        // ftp获取文件
        FTPClient ftpClient = null;
        try {
            ftpClient = getConnection();
            log.info("获取连接");
            ftpClient.changeWorkingDirectory(filePath + fileInfo.getFtpPath());
            // 创建 ByteArrayOutputStream 来接收数据
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 下载文件到 ByteArrayOutputStream
            boolean success = ftpClient.retrieveFile(new String(filePath.concat(fileInfo.getFullPath()).getBytes("UTF-8"), "ISO-8859-1"), outputStream);
            log.info("文件下载结果{}, {}", success, ftpClient.getReplyString());
            if (success) {
                // 将 ByteArrayOutputStream 中的数据转换为字节数组
                byte[] fileData = outputStream.toByteArray();
                return fileData;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 上传文件到FTP服务器
     *
     * @author yz.sun
     * @date 2025/3/27
     */
    public void uploadFileFtp(String filePathAfter, byte[] bytes, String fileName) {
        FTPClient ftpClient = null;
        try {
            // 连接FTP服务器
            ftpClient = getConnection();
            // 不存在目录则创建
            String finalPath = filePath + filePathAfter + "/";
            if (!ftpClient.changeWorkingDirectory(finalPath)) {
                String[] pathArray = finalPath.split("/");
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
            ftpClient.storeFile(new String(finalPath.concat(fileName).getBytes("UTF-8"), "ISO-8859-1"), new java.io.ByteArrayInputStream(bytes));
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
    private FTPClient getConnection() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(60 * 1000 * 6);
        ftpClient.setDataTimeout(60 * 1000 * 30);
        ftpClient.connect(ip, 21);
        ftpClient.enterRemotePassiveMode();
        ftpClient.login(user, password);
        ftpClient.setBufferSize(1024 * 1024 * 10);
        ftpClient.setControlEncoding("SRC_FTP_CODE");
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        return ftpClient;
    }

    /**
     * 保存文件信息
     *
     * @author yz.sun
     * @date 2025/3/27
     */
    public void saveFileId(String uuid, String ftpPath, String fullPath, String originalFilename) {
        fileDao.saveFileId(uuid, ftpPath, fullPath, originalFilename);
    }
}
