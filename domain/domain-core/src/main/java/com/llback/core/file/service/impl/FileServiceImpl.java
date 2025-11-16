package com.llback.core.file.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.llback.common.types.SafeText;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.file.eo.FileEo;
import com.llback.core.file.repository.FileRepository;
import com.llback.core.file.service.FileService;
import com.llback.core.file.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public StringId uploadImage(MultipartFile file) throws IOException {
        // 检查上传文件是否为空
        AssertUtil.notNull(file, "上传文件不能为空");
        AssertUtil.assertFalse(file.isEmpty(), "上传文件不能为空");
        
        // 生成UUID
        String uuid = RandomIdUtil.uuid();
        
        // 上传至服务器
        String filePath;
        try {
            // 以时间为目录
            String format = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            filePath = FtpUtil.uploadFile(format, uuid, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("上传服务器异常", e);
        }
        
        // 落库
        if (StringUtil.isNotEmpty(filePath)) {
            FileEo fileEo = FileEo.builder()
                    .pkId(StringId.of(uuid))
                    .path(SafeText.of(filePath))
                    .fileName(SafeText.of(file.getOriginalFilename()))
                    .createTime(LocalDateTime.now())
                    .build();
            AssertUtil.assertTrue(fileRepository.addFile(fileEo) == 1, "上传文件失败");
        }
        
        return StringId.of(uuid);
    }

    @Override
    public void getImage(StringId imageId, HttpServletResponse response) throws IOException {
        // 从数据库获取文件信息
        FileEo fileEo = fileRepository.getFileById(StringId.of(imageId));
        
        if (fileEo == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        try {
            byte[] file = FtpUtil.getFile(imageId.toString(), fileEo.getPath().toString());
            
            if (file == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            // 设置响应类型为JPEG图片
            response.setContentType("image/jpeg");
            
            // 输出图片
            response.getOutputStream().write(file);
            response.getOutputStream().flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}