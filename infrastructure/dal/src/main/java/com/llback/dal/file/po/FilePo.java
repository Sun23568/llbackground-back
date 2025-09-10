package com.llback.dal.file.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件PO
 * @author yz.sun
 * @date 2025/9/10
 */
@Data
public class FilePo {
    /**
     * 主键
     */
    private String pkId;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
