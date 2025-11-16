package com.llback.api.app.file.dto;

import com.llback.frame.dto.Query;
import lombok.Data;

@Data
public class GetFileContentReq implements Query {
    /**
     * 文件ID
     */
    private String fileId;
}
