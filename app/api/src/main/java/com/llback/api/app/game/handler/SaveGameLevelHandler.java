package com.llback.api.app.game.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.llback.api.app.game.dto.req.SaveGameLevelCmd;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.game.eo.GameLevelEo;
import com.llback.core.game.repository.GameLevelRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 保存游戏关卡处理器。
 */
@Component
@AllArgsConstructor
@HandlerAcl("game:level:save")
public class SaveGameLevelHandler implements Handler<Void, SaveGameLevelCmd> {
    private final GameLevelRepository gameLevelRepository;

    @Override
    public Void execute(SaveGameLevelCmd cmd) {
        AssertUtil.notNull(cmd.getLevelNo(), "关卡编号不能为空");
        AssertUtil.notEmpty(cmd.getLevelName(), "关卡名称不能为空");
        AssertUtil.notEmpty(cmd.getLevelData(), "关卡JSON不能为空");
        validateLevelData(cmd.getLevelData());

        boolean create = StringId.isEmpty(cmd.getPkId());
        StringId pkId = create ? StringId.of(RandomIdUtil.uuid()) : StringId.of(cmd.getPkId());
        GameLevelEo levelEo = GameLevelEo.builder()
                .pkId(pkId)
                .levelNo(cmd.getLevelNo())
                .levelName(cmd.getLevelName())
                .levelData(cmd.getLevelData())
                .enabled(cmd.getEnabled() == null || cmd.getEnabled())
                .sortNo(cmd.getSortNo() == null ? 0 : cmd.getSortNo())
                .build();

        if (create) {
            gameLevelRepository.save(levelEo);
        } else {
            AssertUtil.notNull(gameLevelRepository.findById(pkId), "关卡不存在");
            gameLevelRepository.update(levelEo);
        }
        return Void.TYPE.cast(null);
    }

    private void validateLevelData(String levelData) {
        try {
            JSONObject jsonObject = JSON.parseObject(levelData);
            AssertUtil.assertTrue(jsonObject.containsKey("rows") || jsonObject.containsKey("grainRows"),
                    "关卡JSON必须包含rows或grainRows");
        } catch (JSONException | IllegalArgumentException e) {
            throw new IllegalArgumentException("关卡JSON格式不正确");
        }
    }
}
