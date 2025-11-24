package com.llback.api.app.ai.handler;

import com.llback.api.app.ai.dto.AiConfigDto;
import com.llback.api.app.ai.dto.req.AiConfigListReq;
import com.llback.api.app.ai.dto.resp.AiConfigListResp;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.core.ai.eo.AiConfigEo;
import com.llback.core.ai.repository.AiConfigRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AI菜单配置列表查询处理器
 */
@Component
@HandlerAcl("ai:config:list")
public class QueryAiConfigListHandler implements Handler<AiConfigListResp, AiConfigListReq> {
    /**
     * AI配置仓储
     */
    @Autowired
    private AiConfigRepository aiConfigRepository;

    @Value("${ftp.service.get-image-prefix}")
    private String ftpGetImageUrl;

    @Override
    public AiConfigListResp execute(AiConfigListReq req) {
        // 查询所有AI配置
        List<AiConfigEo> configList = aiConfigRepository.queryAllConfigs();

        // 转换为DTO
        List<AiConfigDto> dtoList = DtoEoAssemblerUtil.eoList2DtoList(configList, AiConfigDto.class);

        // 组装图片URL
        dtoList.forEach(dto -> dto.setBackgroundImage(ftpGetImageUrl + dto.getBackgroundImage()));

        // 构建响应
        AiConfigListResp resp = new AiConfigListResp();
        resp.setUserAccessRespList(dtoList);
        return resp;
    }
}
