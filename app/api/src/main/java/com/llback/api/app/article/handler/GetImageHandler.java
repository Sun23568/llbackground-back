package com.llback.api.app.article.handler;

import com.llback.api.app.article.dto.req.GetImageReq;
import com.llback.common.exception.BizException;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.article.eo.FileEo;
import com.llback.core.article.repository.FileRepository;
import com.llback.frame.Handler;
import com.llback.frame.PubAcl;
import com.llback.rt.common.cache.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取图片处理器
 */
@PubAcl
@Component
public class GetImageHandler implements Handler<Void, GetImageReq> {
    /**
     * ftp工具类
     */
    @Autowired
    private FtpUtil ftpUtil;

    /**
     * 文件仓库
     */
    @Autowired
    private FileRepository fileRepository;

    @Override
    public Void execute(GetImageReq req) {
        AssertUtil.notEmpty(req.getImageId(), "图片ID不能为空");

        // 获取文件
        FileEo fileEo = fileRepository.getFile(StringId.of(req.getImageId()));
        AssertUtil.notNull(fileEo, "图片不存在");
        AssertUtil.notEmpty(fileEo.getPath(), "图片异常");
        AssertUtil.notEmpty(fileEo.getPkId(), "图片异常");
        // 获取文件
        try {
            byte[] file = ftpUtil.getFile(fileEo.getPkId().toString(), fileEo.getPath().toString());
            AssertUtil.notNull(file, "图片异常");
            HttpServletResponse response = req.getResponse();
            response.setContentType("image/jpeg");
            // 输出图片
            response.getOutputStream().write(file);
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new BizException("FTP异常");
        }
        return Void.TYPE.cast(null);
    }
}
