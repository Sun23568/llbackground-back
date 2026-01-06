package com.llback.api.app.ai.handler;

import com.llback.api.app.ai.dto.CharacterCardDto;
import com.llback.api.app.ai.dto.req.QueryCharacterCardListReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.common.types.UserId;
import com.llback.core.ai.eo.CharacterCardEo;
import com.llback.core.ai.repository.CharacterCardRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import com.llback.frame.context.ReqContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询角色卡列表处理器
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Component
@HandlerAcl("ai:character-card:list")
public class QueryCharacterCardListHandler implements Handler<List<CharacterCardDto>, QueryCharacterCardListReq> {

    @Autowired
    private CharacterCardRepository characterCardRepository;

    @Override
    public List<CharacterCardDto> execute(QueryCharacterCardListReq req) {
        // 获取当前用户ID
        UserId userId = ReqContext.userSession().getUserId();

        // 查询当前用户的所有角色卡
        List<CharacterCardEo> characterCardList = characterCardRepository.findByUserId(userId);

        // 过滤有效的角色卡（未删除）
        List<CharacterCardEo> validCards = characterCardList.stream()
                .filter(CharacterCardEo::isValid)
                .collect(Collectors.toList());

        // 转换为DTO
        return DtoEoAssemblerUtil.eoList2DtoList(validCards, CharacterCardDto.class);
    }
}
