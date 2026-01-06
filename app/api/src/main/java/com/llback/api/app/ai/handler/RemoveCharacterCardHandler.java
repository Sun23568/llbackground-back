package com.llback.api.app.ai.handler;

import com.llback.api.app.ai.dto.req.RemoveCharacterCardCmd;
import com.llback.common.types.StringId;
import com.llback.common.types.UserId;
import com.llback.common.util.AssertUtil;
import com.llback.core.ai.eo.CharacterCardEo;
import com.llback.core.ai.repository.CharacterCardRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import com.llback.frame.context.ReqContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 删除角色卡处理器
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Slf4j
@Component
@HandlerAcl("ai:character-card:remove")
public class RemoveCharacterCardHandler implements Handler<Void, RemoveCharacterCardCmd> {

    @Autowired
    private CharacterCardRepository characterCardRepository;

    @Override
    public Void execute(RemoveCharacterCardCmd cmd) {
        // 验证角色卡ID不为空
        AssertUtil.notEmpty(cmd.getCardId(), "角色卡ID不能为空");

        // 查询该角色卡信息
        CharacterCardEo characterCard = characterCardRepository.findById(StringId.of(cmd.getCardId()));
        AssertUtil.notNull(characterCard, "角色卡不存在");

        // 获取当前用户ID
        UserId currentUserId = ReqContext.userSession().getUserId();

        // 只有角色卡的拥有者才能删除
        AssertUtil.assertTrue(characterCard.getUserId().equals(currentUserId),
                "您没有权限删除此角色卡");

        // 验证角色卡未被删除
        AssertUtil.assertTrue(characterCard.isValid(), "角色卡已被删除");

        // 逻辑删除角色卡
        characterCardRepository.deleteById(StringId.of(cmd.getCardId()));

        log.info("用户 {} 成功删除角色卡: {}", currentUserId.getValue(), characterCard.getCardName());

        return Void.TYPE.cast(null);
    }
}
