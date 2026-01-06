package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Command;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传角色卡命令
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Getter
@Builder
public class UploadCharacterCardCmd implements Command {
    /**
     * 角色卡文件
     */
    private MultipartFile file;

    /**
     * 创建命令
     *
     * @param file 角色卡文件
     * @return 上传角色卡命令
     */
    public static UploadCharacterCardCmd of(MultipartFile file) {
        return UploadCharacterCardCmd.builder()
                .file(file)
                .build();
    }
}
