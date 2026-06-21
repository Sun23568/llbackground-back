package com.llback.api.api;

import com.llback.api.app.game.dto.req.QueryGameLevelDetailReq;
import com.llback.api.app.game.dto.req.QueryGameLevelListReq;
import com.llback.api.app.game.dto.req.QueryPublicGameLevelDetailReq;
import com.llback.api.app.game.dto.req.QueryPublicGameLevelsReq;
import com.llback.api.app.game.dto.req.RemoveGameLevelCmd;
import com.llback.api.app.game.dto.req.SaveGameLevelCmd;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 游戏关卡接口。
 */
@RestController
@RequestMapping("/game")
public class GameLevelApi implements RestApi {
    /**
     * 查询后台关卡列表。
     */
    @GetMapping("/level/list")
    public RestResult queryGameLevelList(Integer pageNum, Integer pageRow) {
        return this.execute(QueryGameLevelListReq.of(pageNum, pageRow));
    }

    /**
     * 查询后台关卡详情。
     */
    @PostMapping("/level/detail")
    public RestResult queryGameLevelDetail(@RequestBody QueryGameLevelDetailReq req) {
        return this.execute(req);
    }

    /**
     * 保存后台关卡。
     */
    @PostMapping("/level/save")
    public RestResult saveGameLevel(@RequestBody SaveGameLevelCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 删除后台关卡。
     */
    @PostMapping("/level/delete")
    public RestResult removeGameLevel(@RequestBody RemoveGameLevelCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 查询App公开关卡列表。
     */
    @CrossOrigin(origins = {"http://127.0.0.1:4173", "http://localhost:4173"})
    @GetMapping("/public/levels")
    public RestResult queryPublicGameLevels() {
        return this.execute(QueryPublicGameLevelsReq.EMPTY);
    }

    /**
     * 查询App公开关卡详情。
     */
    @CrossOrigin(origins = {"http://127.0.0.1:4173", "http://localhost:4173"})
    @GetMapping("/public/level/detail")
    public RestResult queryPublicGameLevelDetail(@RequestParam("pkId") String pkId) {
        return this.execute(QueryPublicGameLevelDetailReq.of(pkId));
    }
}
