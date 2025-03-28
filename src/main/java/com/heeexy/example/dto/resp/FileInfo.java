package com.heeexy.example.dto.resp;

import lombok.Data;

/**
 * 文件
 *
 * @author yz.sun
 * @date 2025/3/27
 */
@Data
public class FileInfo {
    /**
     * 文件主键
     */
    private String pkId;

    /**
     * 文件路径
     */
    private String ftpPath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件全路径
     */
    private String fullPath;
}
