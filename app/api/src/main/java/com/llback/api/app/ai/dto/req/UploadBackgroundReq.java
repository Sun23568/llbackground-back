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
     * AI菜单代码（menu_code）
     */
    private String aiMenuCode;

    private UploadBackgroundReq(MultipartFile file, String aiMenuCode) {
        this.file = file;
        this.aiMenuCode = aiMenuCode;
    }

    public static UploadBackgroundReq of(MultipartFile file, String aiMenuCode) {
        return new UploadBackgroundReq(file, aiMenuCode);
    }
}
