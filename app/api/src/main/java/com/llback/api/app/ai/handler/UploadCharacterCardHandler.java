package com.llback.api.app.ai.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.llback.api.app.ai.dto.req.UploadCharacterCardCmd;
import com.llback.common.exception.BizException;
import com.llback.common.types.SafeText;
import com.llback.common.types.StringId;
import com.llback.common.types.UserId;
import com.llback.common.util.AssertUtil;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.ai.eo.CharacterCardEo;
import com.llback.core.ai.repository.CharacterCardRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import com.llback.frame.context.ReqContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 上传角色卡处理器
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Slf4j
@Component
@HandlerAcl("ai:upload-character-card")
public class UploadCharacterCardHandler implements Handler<JSONObject, UploadCharacterCardCmd> {

    @Autowired
    private CharacterCardRepository characterCardRepository;

    @Override
    public JSONObject execute(UploadCharacterCardCmd cmd) {
        // 1. 验证文件不为空
        AssertUtil.notNull(cmd.getFile(), "角色卡文件不能为空");
        AssertUtil.assertTrue(!cmd.getFile().isEmpty(), "角色卡文件不能为空");

        // 2. 验证文件类型
        String originalFilename = cmd.getFile().getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename) || !originalFilename.toLowerCase().endsWith(".json")) {
            throw new BizException("文件格式错误，仅支持JSON格式");
        }

        // 3. 读取文件内容
        String content;
        try {
            content = new String(cmd.getFile().getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("读取文件内容失败", e);
            throw new BizException("读取文件失败: " + e.getMessage());
        }

        // 4. 验证JSON格式并提取信息
        JSONObject cardJson;
        try {
            cardJson = JSON.parseObject(content);
        } catch (Exception e) {
            log.error("解析JSON失败", e);
            throw new BizException("JSON格式错误: " + e.getMessage());
        }

        // 5. 提取角色卡名称和描述
        JSONObject cardData = cardJson.getJSONObject("data");
        String cardName = cardData.getString("name");
        if (StringUtils.isEmpty(cardName)) {
            throw new BizException("角色卡名称不能为空（JSON中必须包含'name'字段）");
        }
        String cardDescription = cardData.getString("description");

        // 6. 获取当前用户ID
        UserId userId = ReqContext.userSession().getUserId();

        // 7. 构建角色卡实体并保存
        CharacterCardEo characterCardEo = CharacterCardEo.builder()
                .id(StringId.of(RandomIdUtil.uuid()))
                .userId(UserId.of(userId.getValue()))
                .cardName(cardName)
                .cardDescription(cardDescription)
                .cardContent(cardJson.toString())
                .deleteStatus(1)
                .build();

        characterCardRepository.save(characterCardEo);

        // 8. 返回结果
        JSONObject result = new JSONObject();
        result.put("cardName", cardName);
        result.put("cardDescription", cardDescription);
        result.put("message", "角色卡上传成功");

        log.info("用户 {} 成功上传角色卡: {}", userId.getValue(), cardName);

        return result;
    }
}
