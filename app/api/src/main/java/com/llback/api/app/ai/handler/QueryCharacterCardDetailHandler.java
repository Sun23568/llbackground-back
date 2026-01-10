package com.llback.api.app.ai.handler;

import com.llback.api.app.ai.dto.CharacterCardDto;
import com.llback.api.app.ai.dto.req.QueryCharacterCardDetailReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.ai.eo.CharacterCardEo;
import com.llback.core.ai.repository.CharacterCardRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 查询角色卡详情处理器
 *
 * @author HaleyAI
 * @date 2026/1/11
 */
@Component
@AllArgsConstructor
@HandlerAcl("ai:character-card:detail")
public class QueryCharacterCardDetailHandler implements Handler<CharacterCardDto, QueryCharacterCardDetailReq> {

    private final CharacterCardRepository characterCardRepository;

    @Override
    public CharacterCardDto execute(QueryCharacterCardDetailReq req) {
        // 参数校验
        AssertUtil.notEmpty(req.getCardId(), "角色卡ID不能为空");

        // 查询角色卡
        CharacterCardEo card = characterCardRepository.findById(StringId.of(req.getCardId()));
        AssertUtil.notNull(card, "角色卡不存在");
        AssertUtil.assertTrue(card.isValid(), "角色卡已被删除");

        // 转换为DTO返回
        return DtoEoAssemblerUtil.eo2Dto(card, CharacterCardDto.class);
    }
}
