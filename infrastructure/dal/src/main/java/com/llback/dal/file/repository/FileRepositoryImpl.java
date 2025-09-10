package com.llback.dal.file.repository;

import com.llback.common.types.StringId;
import com.llback.core.article.eo.FileEo;
import com.llback.core.article.repository.FileRepository;
import com.llback.dal.file.dao.FileDao;
import com.llback.dal.file.po.FilePo;
import com.llback.rt.common.cache.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 文件仓库实现
 *
 * @author yz.sun
 * @date 2025/9/10
 */
@Component
public class FileRepositoryImpl implements FileRepository {
    /**
     * 文件Dao
     */
    @Autowired
    private FileDao fileDao;

    /**
     * 添加文件
     */
    @Override
    public int addFile(FileEo fileEo) {
        FilePo filePo = PoAssembleUtil.eo2Po(fileEo, FilePo.class);
        return fileDao.addFile(filePo);
    }

    /**
     * 获取文件
     */
    @Override
    public FileEo getFile(StringId imageId) {
        FilePo filePo = fileDao.getFileById(imageId.toString());
        return PoAssembleUtil.po2Eo(filePo, FileEo.class);
    }
}
