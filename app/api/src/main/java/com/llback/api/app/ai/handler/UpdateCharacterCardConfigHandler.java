package com.llback.api.app.ai.handler;

import com.llback.api.app.ai.dto.req.UpdateCharacterCardConfigCmd;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.ai.eo.CharacterCardEo;
import com.llback.core.ai.repository.CharacterCardRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 更新角色卡配置处理器
 *
 * @author HaleyAI
 * @date 2026/1/8
 */
@Component
@AllArgsConstructor
@HandlerAcl("ai:character-card:update-config")
public class UpdateCharacterCardConfigHandler implements Handler<Void, UpdateCharacterCardConfigCmd> {

    private final CharacterCardRepository characterCardRepository;

    @Override
    public Void execute(UpdateCharacterCardConfigCmd cmd) {
        // 参数校验
        AssertUtil.notEmpty(cmd.getId(), "角色卡ID不能为空");
        AssertUtil.notEmpty(cmd.getCardName(), "角色名称不能为空");

        // 查询现有角色卡
        CharacterCardEo existingCard = characterCardRepository.findById(StringId.of(cmd.getId()));
        AssertUtil.notNull(existingCard, "角色卡不存在");
        AssertUtil.assertTrue(existingCard.isValid(), "角色卡已被删除");

        // 构建更新后的实体（遵循SOLID原则：使用Builder模式重新创建不可变对象）
        CharacterCardEo updatedCard = CharacterCardEo.builder()
            .id(existingCard.getId())
            .userId(existingCard.getUserId())
            .cardName(cmd.getCardName())
            .userName(cmd.getUserName())
            .cardDescription(existingCard.getCardDescription())
            .cardContent(existingCard.getCardContent())
            .initialPrompt(cmd.getInitialPrompt())
            .deleteStatus(existingCard.getDeleteStatus())
            .createTime(existingCard.getCreateTime())
            .updateTime(existingCard.getUpdateTime())
            .build();

        // 保存更新
        characterCardRepository.update(updatedCard);

        return Void.TYPE.cast(null);
    }
}
