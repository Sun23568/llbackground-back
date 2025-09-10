package com.llback.core.article.repository;

import com.llback.common.types.StringId;
import com.llback.core.article.eo.FileEo;

/**
 * 文件仓库
 *
 * @author yz.sun
 * @date 2025/9/10
 */
public interface FileRepository {
    /**
     * 添加文件
     */
    int addFile(FileEo fileEo);

    /**
     * 获取文件
     */
    FileEo getFile(StringId imageId);
}
