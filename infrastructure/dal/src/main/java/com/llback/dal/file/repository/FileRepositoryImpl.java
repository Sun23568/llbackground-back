package com.llback.dal.file.repository;

import com.llback.common.types.StringId;
import com.llback.core.file.eo.FileEo;
import com.llback.core.file.repository.FileRepository;
import com.llback.dal.file.dao.FileDao;
import com.llback.dal.file.po.FilePo;
import com.llback.rt.common.util.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 文件数据访问接口实现
 */
@Component
public class FileRepositoryImpl implements FileRepository {
    /**
     * 文件数据访问接口
     */
    @Autowired
    private FileDao fileDao;

    /**
     * 添加文件
     */
    @Override
    public int addFile(FileEo fileEo) {
        return fileDao.addFile(PoAssembleUtil.eo2Po(fileEo, FilePo.class));
    }

    /**
     * 根据文件ID获取文件信息
     */
    @Override
    public FileEo getFileById(StringId fileId) {
        return PoAssembleUtil.po2Eo(fileDao.getFileById(fileId.toString()), FileEo.class);
    }
}
