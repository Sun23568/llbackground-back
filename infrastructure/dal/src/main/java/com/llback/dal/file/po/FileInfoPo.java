package com.llback.dal.file.po;

import lombok.Data;

@Data
public class FileInfoPo {
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
