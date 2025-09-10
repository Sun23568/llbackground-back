package com.llback.api.app.article.dto.req;

import com.llback.frame.dto.Command;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文章上传图片命令
 */
@Data
public class ArticleUploadImageCmd implements Command {
    /**
     * 文件
     */
    private MultipartFile file;

    public ArticleUploadImageCmd(MultipartFile file) {
        this.file = file;
    }

    /**
     * 创建命令
     *
     * @author yz.sun
     * @date 2025/9/10
     */
    public static ArticleUploadImageCmd of(MultipartFile file){
        return new ArticleUploadImageCmd(file);
    }
}
