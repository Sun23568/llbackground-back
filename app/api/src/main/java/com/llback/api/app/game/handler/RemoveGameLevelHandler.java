package com.llback.api.app.game.handler;

import com.llback.api.app.game.dto.req.RemoveGameLevelCmd;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.game.repository.GameLevelRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 删除游戏关卡处理器。
 */
@Component
@AllArgsConstructor
@HandlerAcl("game:level:remove")
public class RemoveGameLevelHandler implements Handler<Void, RemoveGameLevelCmd> {
    private final GameLevelRepository gameLevelRepository;

    @Override
    public Void execute(RemoveGameLevelCmd cmd) {
        AssertUtil.notEmpty(cmd.getPkId(), "关卡ID不能为空");
        gameLevelRepository.deleteById(StringId.of(cmd.getPkId()));
        return Void.TYPE.cast(null);
    }
}
