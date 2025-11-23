package com.llback.api.app.ai.handler;

import com.llback.api.app.ai.dto.req.UpdateAiConfigCmd;
import com.llback.api.app.menu.dto.MenuDto;
import com.llback.api.app.menu.fetch.MenuFetch;
import com.llback.common.util.AssertUtil;
import com.llback.core.ai.eo.AiConfigEo;
import com.llback.core.ai.repository.AiConfigRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 更新AI配置处理器
 *
 * @author llback
 */
@Component
@HandlerAcl("ai:config:update")
public class UpdateAiConfigHandler implements Handler<Void, UpdateAiConfigCmd> {
    /**
     * AI配置仓储
     */
    @Autowired
    private AiConfigRepository aiConfigRepository;

    /**
     * 菜单Fetch
     */
    @Autowired
    private MenuFetch menuFetch;

    @Override
    public Void execute(UpdateAiConfigCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getMenuCode(), "菜单代码不能为空");

        // 通过菜单代码查询菜单
        MenuDto menuDto = menuFetch.queryMenuByCode(cmd.getMenuCode());
        AssertUtil.notNull(menuDto, "菜单不存在");

        // 查询现有配置
        AiConfigEo existConfig = aiConfigRepository.queryByMenuCode(cmd.getMenuCode());
        AssertUtil.notNull(existConfig, "配置不存在");

        // 构建更新实体对象
        AiConfigEo aiConfigEo = AiConfigEo.builder()
                .pkId(existConfig.getPkId())
                .menuId(existConfig.getMenuId())
                .ollamaModelId(cmd.getOllamaModelId())
                .comfyUiUrl(cmd.getComfyUiUrl())
                .ollamaUrl(cmd.getOllamaUrl())
                .comfyFileId(cmd.getComfyFileId())
                .backgroundImage(cmd.getBackgroundImage())
                .contextSize(cmd.getContextSize() != null ? cmd.getContextSize() : 1024)
                .createTime(existConfig.getCreateTime())
                .updateTime(LocalDateTime.now())
                .build();

        // 更新到仓储
        aiConfigRepository.updateAiConfig(aiConfigEo);

        return null;
    }
}
