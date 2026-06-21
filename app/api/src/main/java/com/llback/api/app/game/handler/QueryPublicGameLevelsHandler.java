package com.llback.api.app.game.handler;

import com.llback.api.app.game.dto.GameLevelDto;
import com.llback.api.app.game.dto.req.QueryPublicGameLevelsReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.core.game.repository.GameLevelRepository;
import com.llback.frame.Handler;
import com.llback.frame.PubAcl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 查询公开游戏关卡列表处理器。
 */
@Component
@AllArgsConstructor
@PubAcl
public class QueryPublicGameLevelsHandler implements Handler<List<GameLevelDto>, QueryPublicGameLevelsReq> {
    private final GameLevelRepository gameLevelRepository;

    @Override
    public List<GameLevelDto> execute(QueryPublicGameLevelsReq req) {
        List<GameLevelDto> list = DtoEoAssemblerUtil.eoList2DtoList(gameLevelRepository.findEnabled(), GameLevelDto.class);
        list.forEach(item -> item.setLevelData(null));
        return list;
    }
}
