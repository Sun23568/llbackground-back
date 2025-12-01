package com.llback.api.app.ai.handler;

import com.llback.api.app.ai.dto.req.AddAiConfigCmd;
import com.llback.api.app.menu.dto.MenuDto;
import com.llback.api.app.menu.fetch.MenuFetch;
import com.llback.common.types.MenuCode;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.ai.eo.AiConfigEo;
import com.llback.core.ai.repository.AiConfigRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 新增AI配置处理器
 *
 * @author llback
 */
@Component
@HandlerAcl("ai:config:add")
public class AddAiConfigHandler implements Handler<Void, AddAiConfigCmd> {
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
    public Void execute(AddAiConfigCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getMenuCode(), "菜单代码不能为空");

        // 通过菜单代码查询菜单
        MenuDto menuDto = menuFetch.queryMenuByCode(cmd.getMenuCode());
        AssertUtil.notNull(menuDto, "菜单不存在");

        // 校验该菜单是否已配置
        AiConfigEo existConfig = aiConfigRepository.queryByMenuCode(cmd.getMenuCode());
        AssertUtil.isNull(existConfig, "该菜单已配置AI");

        // 构建实体对象
        LocalDateTime now = LocalDateTime.now();
        AiConfigEo aiConfigEo = AiConfigEo.builder()
                .pkId(StringId.of(RandomIdUtil.uuid()))
                .menuCode(MenuCode.of(menuDto.getMenuCode()))
                .ollamaModelId(cmd.getOllamaModelId())
                .comfyUiUrl(cmd.getComfyUiUrl())
                .ollamaUrl(cmd.getOllamaUrl())
                .comfyFileId(cmd.getComfyFileId())
                .backgroundImage(cmd.getBackgroundImage())
                .contextSize(cmd.getContextSize() != null ? cmd.getContextSize() : 1024)
                .initialCharacterState(cmd.getInitialCharacterState())
                .createTime(now)
                .updateTime(now)
                .build();

        // 保存到仓储
        aiConfigRepository.addAiConfig(aiConfigEo);

        return null;
    }
}
