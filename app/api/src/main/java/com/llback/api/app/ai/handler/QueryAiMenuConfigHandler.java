package com.llback.api.app.ai.handler;

import com.github.pagehelper.util.StringUtil;
import com.llback.api.app.ai.dto.AiConfigDto;
import com.llback.api.app.ai.dto.req.AiConfigReq;
import com.llback.api.app.ai.fetch.AiConfigFetch;
import com.llback.common.types.StringId;
import com.llback.frame.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * AI配置-查询菜单配置处理器
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Component
public class QueryAiMenuConfigHandler implements Handler<AiConfigDto, AiConfigReq> {
    /**
     * AI模块菜单配置查询
     */
    @Autowired
    private AiConfigFetch aiConfigFetch;

    /**
     * ftp文件服务获取图片的URL前缀
     */
    @Value("${ftp.service.get-image-prefix}")
    private String ftpGetImageUrl;

    @Override
    public AiConfigDto execute(AiConfigReq req) {
        // 非法返回空
        if (StringUtils.isEmpty(req.getAiMenuCode())) {
            return new AiConfigDto();
        }
        AiConfigDto aiConfigDto = aiConfigFetch.queryAiConfig(StringId.of(req.getAiMenuCode()));
        // 无配置则返回空
        if (aiConfigDto == null) {
            return new AiConfigDto();
        }
        // 如果背景图片不为空，则拼接URL
        String fileId = aiConfigDto.getBackgroundImage();
        if (StringUtil.isNotEmpty(fileId)) {
            aiConfigDto.setBackgroundImage(ftpGetImageUrl + fileId);
        }
        return aiConfigDto;
    }
}
