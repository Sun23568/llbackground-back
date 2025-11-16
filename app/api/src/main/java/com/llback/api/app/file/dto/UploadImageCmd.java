package com.llback.api.app.file.dto;

import com.llback.frame.dto.Command;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文章上传图片命令
 */
@Data
public class UploadImageCmd implements Command {
    /**
     * 文件
     */
    private MultipartFile file;

    public UploadImageCmd(MultipartFile file) {
        this.file = file;
    }

    /**
     * 创建命令
     *
     * @author yz.sun
     * @date 2025/9/10
     */
    public static UploadImageCmd of(MultipartFile file){
        return new UploadImageCmd(file);
    }
}
