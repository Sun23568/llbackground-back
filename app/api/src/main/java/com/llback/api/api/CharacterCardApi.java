package com.llback.api.api;

import com.llback.api.app.ai.dto.req.QueryCharacterCardListReq;
import com.llback.api.app.ai.dto.req.RemoveCharacterCardCmd;
import com.llback.api.app.ai.dto.req.UploadCharacterCardCmd;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 角色卡管理接口
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@RestController
@RequestMapping("/character-card")
public class CharacterCardApi implements RestApi {

    /**
     * 上传角色卡
     */
    @PostMapping("/upload")
    public RestResult uploadCharacterCard(@RequestParam("file") MultipartFile file) {
        return this.execute(UploadCharacterCardCmd.of(file));
    }

    /**
     * 查询角色卡列表
     */
    @GetMapping("/list")
    public RestResult queryCharacterCardList() {
        return this.execute(QueryCharacterCardListReq.EMPTY);
    }

    /**
     * 删除角色卡
     */
    @PostMapping("/remove")
    public RestResult removeCharacterCard(@RequestBody RemoveCharacterCardCmd cmd) {
        return this.execute(cmd);
    }
}
