package com.llback.api.app.file.dto;

import com.llback.frame.dto.Command;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * 获取图片请求
 */
@Data
public class GetImageReq implements Command {
    /**
     * 图片ID
     */
    private String imageId;

    /**
     * 响应
     */
    private HttpServletResponse response;

    public GetImageReq(String imageId, HttpServletResponse response) {
        this.imageId = imageId;
        this.response = response;
    }

    /**
     * 创建命令
     *
     * @author yz.sun
     * @date 2025/9/10
     */
    public static GetImageReq of(String imageId, HttpServletResponse response) {
        return new GetImageReq(imageId, response);
    }
}
