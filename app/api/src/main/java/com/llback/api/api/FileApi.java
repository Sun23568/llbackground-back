package com.llback.api.api;

import com.llback.api.app.file.dto.GetFileContentReq;
import com.llback.api.app.file.dto.GetImageReq;
import com.llback.api.app.file.dto.UploadImageCmd;
import com.llback.frame.rest.RestApi;
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
