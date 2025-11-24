package com.llback.api.app.ai.handler;

import com.llback.api.app.ai.dto.req.UploadBackgroundReq;
import com.llback.common.types.StringId;
import com.llback.core.ai.repository.AiModelRepository;
import com.llback.core.article.feign.FtpFileFeign;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * AI配置-上传背景图片处理器
 *
 * @author yz.sun 孙延昭
 * @date 2025/8/22
 */
@Component
@HandlerAcl("ai:config:background-image:upload")
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
    private AiModelRepository aiModelRepository;

    /**
     * ftp文件服务获取图片的URL前缀
     */
    @Value("${ftp.service.get-image-prefix}")
    private String ftpGetImageUrl;

    /**
     * 执行上传背景图片操作
     *
     * @param req 上传背景图片请求参数
     * @return 返回图片访问URL
     * @author yz.sun 孙延昭
     */
    @Override
    @Transactional
    public String execute(UploadBackgroundReq req) {
        // 上传文件
        ResponseEntity<String> fileId = feign.uploadFile(req.getFile());
        // 更新AI菜单背景图片
        aiModelRepository.updateBackgroundImage(StringId.of(req.getAiMenuCode()), StringId.of(fileId.getBody()));
        return ftpGetImageUrl + fileId.getBody();
    }
}
