package com.llback.api.app.file.dto;

import com.llback.frame.dto.Command;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传命令
 */
@Getter
@Builder
public class UploadFileCmd implements Command {
    /**
     * 文件
     */
    private MultipartFile file;

    /**
     * 自定义文件ID（可选，如果不提供则自动生成UUID）
     */
    private String customFileId;

    /**
     * 是否替换已存在的文件（当customFileId对应的文件已存在时）
     */
    private Boolean replace;

    /**
     * 创建命令
     *
     * @param file 文件
     * @param customFileId 自定义文件ID
     * @param replace 是否替换
     * @return 上传文件命令
     */
    public static UploadFileCmd of(MultipartFile file, String customFileId, Boolean replace) {
        return UploadFileCmd.builder()
                .file(file)
                .customFileId(customFileId)
                .replace(replace != null ? replace : false)
                .build();
    }
}
