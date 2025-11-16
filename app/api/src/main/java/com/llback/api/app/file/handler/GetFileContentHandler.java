package com.llback.api.app.file.handler;

import com.llback.api.app.file.dto.GetFileContentReq;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.file.eo.FileEo;
import com.llback.core.file.repository.FileRepository;
import com.llback.frame.Handler;
import com.llback.frame.PubAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.llback.core.file.util.FtpUtil;
import java.io.IOException;

/**
 * 获取图片处理器
 */
@PubAcl
@Component
public class GetFileContentHandler implements Handler<String, GetFileContentReq> {
    /**
     * 文件仓库
     */
    @Autowired
    private FileRepository fileRepository;

    @Override
    public String execute(GetFileContentReq req) {
        AssertUtil.notEmpty(req.getFileId(), "文件ID不能为空");

        // 获取文件
        FileEo fileById = fileRepository.getFileById(StringId.of(req.getFileId()));
        AssertUtil.notNull(fileById, "文件不存在");
        try {
            byte[] file = FtpUtil.getFile(fileById.getFileName().toString(), fileById.getPath().toString());
            return new String(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
