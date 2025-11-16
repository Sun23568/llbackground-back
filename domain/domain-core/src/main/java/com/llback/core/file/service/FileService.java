package com.llback.core.file.service;

import com.llback.common.types.StringId;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileService {
    StringId uploadImage(MultipartFile file) throws IOException;
    
    void getImage(StringId imageId, HttpServletResponse response) throws IOException;
}
