package com.llback.dal.file.dao;

import com.llback.dal.file.po.FilePo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件仓储
 */
@Mapper
public interface FileDao {
    /**
     * 添加文件
     */
    int addFile(FilePo filePo);
}
