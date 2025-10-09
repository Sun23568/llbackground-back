package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadBackgroundReq implements Query {
    /**
     * 文件
     */
    private MultipartFile file;

    /**
     * AI菜单ID
     */
    private String aiMenuId;

    private UploadBackgroundReq(MultipartFile file, String aiMenuId) {
        this.file = file;
        this.aiMenuId = aiMenuId;
    }

    public static UploadBackgroundReq of(MultipartFile file, String aiMenuId) {
        return new UploadBackgroundReq(file, aiMenuId);
    }
}
