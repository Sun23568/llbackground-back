package com.llback.core.file.repository;

import com.llback.common.types.StringId;
import com.llback.core.file.eo.FileEo;

public interface FileRepository {
    /**
     * 添加文件信息到数据库
     * @param fileEo 文件信息
     * @return 插入记录数
     */
    int addFile(FileEo fileEo);

    /**
     * 根据文件ID获取文件信息
     * @param fileId 文件ID
     * @return 文件信息
     */
    FileEo getFileById(StringId fileId);

    /**
     * 更新文件信息
     * @param fileEo 文件信息
     * @return 更新记录数
     */
    int updateFile(FileEo fileEo);

    /**
     * 删除文件信息
     * @param fileId 文件ID
     * @return 删除记录数
     */
    int deleteFile(StringId fileId);
}
