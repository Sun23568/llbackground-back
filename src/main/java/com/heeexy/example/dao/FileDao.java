package com.heeexy.example.dao;

import com.heeexy.example.dto.resp.FileInfo;

/**
 * 文件仓储
 *
 * @author yz.sun
 * @date 2025/3/27
 */
public interface FileDao {

    /**
     * 保存文件
     *
     * @author yz.sun
     * @date 2025/3/27
     */
    void saveFileId(String uuid, String ftpPath, String fullPath, String fileName);

    /**
     * 根据文件id获取文件信息
     *
     * @author yz.sun
     * @date 2025/3/27
     */
    FileInfo getFileById(String fileId);
}
