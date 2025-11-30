package com.llback.api.api;

import com.llback.api.app.file.dto.GetFileContentReq;
import com.llback.api.app.file.dto.GetImageReq;
import com.llback.api.app.file.dto.UploadImageCmd;
import com.llback.api.app.file.dto.UploadFileCmd;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileApi implements RestApi {
    @PostMapping("/upload/image")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return (String) this.execute(UploadImageCmd.of(file)).getData();
    }

    /**
     * 上传文件（支持自定义file_id和替换功能）
     * @param file 文件
     * @param customFileId 自定义文件ID（可选）
     * @param replace 是否替换已存在的文件（默认false）
     * @return 上传结果
     */
    @PostMapping("/upload")
    public RestResult uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "customFileId", required = false) String customFileId,
            @RequestParam(value = "replace", required = false, defaultValue = "false") Boolean replace) {
        return this.execute(UploadFileCmd.of(file, customFileId, replace));
    }

    /**
     * 获取图片
     */
    @GetMapping("/image/{imageId}")
    public void getImage(@PathVariable String imageId, HttpServletResponse response) {
        this.execute(GetImageReq.of(imageId, response)).getData();
    }

    @PostMapping("/content")
    public String getFileContent(@RequestBody GetFileContentReq getFileReq){
        return (String) this.execute(getFileReq).getData();
    }
}
