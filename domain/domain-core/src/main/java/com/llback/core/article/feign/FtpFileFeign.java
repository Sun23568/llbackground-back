package com.llback.core.article.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * FTP文件服务接口
 */
@FeignClient(name = "ftp-file-service", url = "${ftp.service.url:}")
public interface FtpFileFeign {
    /**
     * 上传文件到FTP服务器
     *
     * @param file 文件内容
     * @return 文件ID
     */
    @PostMapping(value = "/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file);
}
