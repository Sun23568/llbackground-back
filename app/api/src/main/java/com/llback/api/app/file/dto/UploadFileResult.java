package com.llback.api.app.file.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 文件上传结果
 */
@Getter
@Builder
public class UploadFileResult {
    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 是否替换了已存在的文件
     */
    private Boolean replaced;

    /**
     * 上传时间戳
     */
    private String uploadTime;

    /**
     * 创建上传成功结果
     *
     * @param fileId 文件ID
     * @param fileName 文件名
     * @param replaced 是否替换
     * @param uploadTime 上传时间
     * @return 上传结果
     */
    public static UploadFileResult of(String fileId, String fileName, Boolean replaced, String uploadTime) {
        return UploadFileResult.builder()
                .fileId(fileId)
                .fileName(fileName)
                .replaced(replaced)
                .uploadTime(uploadTime)
                .build();
    }
}
