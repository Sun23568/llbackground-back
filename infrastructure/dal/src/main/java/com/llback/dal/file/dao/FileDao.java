package com.llback.dal.file.dao;

import com.llback.dal.file.po.FilePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileDao {
    /**
     * 添加文件信息
     *
     * @param filePo
     * @return
     */
    int addFile(FilePo filePo);

    /**
     * 根据文件ID获取文件信息
     *
     * @param fileId
     * @return
     */
    FilePo getFileById(@Param("fileId") String fileId);
}
