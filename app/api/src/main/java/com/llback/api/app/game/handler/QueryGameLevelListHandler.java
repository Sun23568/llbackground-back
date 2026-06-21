package com.llback.api.app.game.handler;

import com.github.pagehelper.PageInfo;
import com.llback.api.app.game.dto.GameLevelDto;
import com.llback.api.app.game.dto.req.QueryGameLevelListReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.core.game.eo.GameLevelEo;
import com.llback.core.game.repository.GameLevelRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询游戏关卡列表处理器。
 */
@Component
@AllArgsConstructor
@HandlerAcl("game:level:list")
public class QueryGameLevelListHandler implements Handler<Map<String, Object>, QueryGameLevelListReq> {
    private final GameLevelRepository gameLevelRepository;

    @Override
    public Map<String, Object> execute(QueryGameLevelListReq req) {
        PageInfo<GameLevelEo> pageInfo = gameLevelRepository.findAll(req.getPageNum(), req.getPageRow());
        Map<String, Object> result = new HashMap<>();
        result.put("list", DtoEoAssemblerUtil.eoList2DtoList(pageInfo.getList(), GameLevelDto.class));
        result.put("total", pageInfo.getTotal());
        return result;
    }
}
