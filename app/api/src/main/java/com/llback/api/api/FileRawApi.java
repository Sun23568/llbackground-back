package com.llback.api.api;

import com.llback.common.types.StringId;
import com.llback.core.file.eo.FileEo;
import com.llback.core.file.repository.FileRepository;
import com.llback.core.file.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLConnection;

/**
 * 文件原始内容读取接口。
 */
@RestController
@RequestMapping("/file")
public class FileRawApi {

    @Autowired
    private FileRepository fileRepository;

    /**
     * 读取原始文件内容。
     */
    @GetMapping("/raw/{fileId}")
    public void getRawFile(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
        FileEo fileEo = fileRepository.getFileById(StringId.of(fileId));
        if (fileEo == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        byte[] bytes = FtpUtil.getFile(fileId, fileEo.getPath().toString());
        if (bytes == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = URLConnection.guessContentTypeFromName(fileEo.getFileName().toString());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "public, max-age=31536000, immutable");
        response.setContentType(contentType);
        response.setContentLengthLong(bytes.length);
        response.getOutputStream().write(bytes);
    }
}
