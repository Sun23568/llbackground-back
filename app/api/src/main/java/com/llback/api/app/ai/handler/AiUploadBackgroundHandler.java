package com.llback.api.app.ai.handler;

import com.llback.api.app.ai.dto.req.UploadBackgroundReq;
import com.llback.common.types.StringId;
import com.llback.core.ai.repository.AiRepository;
import com.llback.core.article.feign.FtpFileFeign;
import com.llback.core.article.service.ArticleService;
import com.llback.frame.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * AI模块上传背景图片处理器
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Component
public class AiUploadBackgroundHandler implements Handler<String, UploadBackgroundReq> {
    /**
     * ftp文件服务
     */
    @Lazy
    @Autowired
    private FtpFileFeign feign;

    /**
     * AI仓库
     */
    @Autowired
    private AiRepository aiRepository;

    /**
     * ftp文件服务获取图片的URL前缀
     */
    @Value("${ftp.service.get-image-prefix}")
    private String ftpGetImageUrl;

    @Override
    @Transactional
    public String execute(UploadBackgroundReq req) {
        // 上传文件
        ResponseEntity<String> fileId = feign.uploadFile(req.getFile());
        // 更新AI菜单背景图片
        aiRepository.updateBackground(StringId.of(req.getAiMenuId()), StringId.of(fileId.getBody()));
        return ftpGetImageUrl + fileId.getBody();
    }
}
