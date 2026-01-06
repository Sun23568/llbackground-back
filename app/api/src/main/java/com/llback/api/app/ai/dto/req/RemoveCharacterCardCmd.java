package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;

/**
 * 删除角色卡命令
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Data
public class RemoveCharacterCardCmd implements Command {
    /**
     * 角色卡ID
     */
    private String cardId;
}
