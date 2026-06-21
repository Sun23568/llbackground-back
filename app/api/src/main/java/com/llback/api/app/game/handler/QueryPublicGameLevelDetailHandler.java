package com.llback.api.app.game.handler;

import com.llback.api.app.game.dto.GameLevelDto;
import com.llback.api.app.game.dto.req.QueryPublicGameLevelDetailReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.game.eo.GameLevelEo;
import com.llback.core.game.repository.GameLevelRepository;
import com.llback.frame.Handler;
import com.llback.frame.PubAcl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 查询公开游戏关卡详情处理器。
 */
@Component
@AllArgsConstructor
@PubAcl
public class QueryPublicGameLevelDetailHandler implements Handler<GameLevelDto, QueryPublicGameLevelDetailReq> {
    private final GameLevelRepository gameLevelRepository;

    @Override
    public GameLevelDto execute(QueryPublicGameLevelDetailReq req) {
        AssertUtil.notEmpty(req.getPkId(), "关卡ID不能为空");
        GameLevelEo levelEo = gameLevelRepository.findById(StringId.of(req.getPkId()));
        AssertUtil.notNull(levelEo, "关卡不存在");
        AssertUtil.assertTrue(levelEo.getEnabled() != null && levelEo.getEnabled(), "关卡未发布");
        return DtoEoAssemblerUtil.eo2Dto(levelEo, GameLevelDto.class);
    }
}
