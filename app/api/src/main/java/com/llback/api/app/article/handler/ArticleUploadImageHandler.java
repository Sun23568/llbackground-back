package com.llback.api.app.article.handler;

import com.llback.api.app.article.dto.req.ArticleUploadImageCmd;
import com.llback.common.exception.BizException;
import com.llback.common.types.SafeText;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.article.eo.FileEo;
import com.llback.core.article.repository.FileRepository;
import com.llback.frame.Handler;
import com.llback.rt.common.cache.FtpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.net.ftp.FtpProtocolException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 修改文章处理器
 */
@Component
public class ArticleUploadImageHandler implements Handler<String, ArticleUploadImageCmd> {
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
    public String execute(ArticleUploadImageCmd cmd) {
        AssertUtil.notNull(cmd.getFile(), "上传文件不能为空");
        // 生成UUID
        String uuid = RandomIdUtil.uuid();
        // 上传至服务器
        String filePath;
        try {
            // 以时间为目录
            String format = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            filePath = ftpUtil.uploadFile(format, uuid, cmd.getFile().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizException("上传服务器异常");
        }
        // 落库
        if (StringUtils.isNotEmpty(filePath)) {
            FileEo fileEo = FileEo.builder()
                    .pkId(StringId.of(uuid))
                    .path(SafeText.of(filePath))
                    .fileName(SafeText.of(cmd.getFile().getOriginalFilename()))
                    .createTime(LocalDateTime.now())
                    .build();
            AssertUtil.assertTrue(fileRepository.addFile(fileEo) == 1, "上传文件失败");
        }
        return uuid;
    }
}
