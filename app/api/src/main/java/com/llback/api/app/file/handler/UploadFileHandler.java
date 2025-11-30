package com.llback.api.app.file.handler;

import com.llback.api.app.file.dto.UploadFileCmd;
import com.llback.api.app.file.dto.UploadFileResult;
import com.llback.common.exception.BizException;
import com.llback.common.types.SafeText;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.file.eo.FileEo;
import com.llback.core.file.repository.FileRepository;
import com.llback.core.file.util.FtpUtil;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 文件上传处理器（支持自定义file_id和替换功能）
 */
@Slf4j
@Component
@HandlerAcl("file:upload")
public class UploadFileHandler implements Handler<UploadFileResult, UploadFileCmd> {

    /**
     * 文件仓库
     */
    @Autowired
    private FileRepository fileRepository;

    @Override
    public UploadFileResult execute(UploadFileCmd cmd) {
        // 1. 验证文件不为空
        AssertUtil.notNull(cmd.getFile(), "上传文件不能为空");
        AssertUtil.assertTrue(!cmd.getFile().isEmpty(), "上传文件不能为空");

        // 2. 确定要使用的文件ID
        String fileId = StringUtils.isNotBlank(cmd.getCustomFileId())
                ? cmd.getCustomFileId().trim()
                : RandomIdUtil.uuid();

        // 3. 检查文件是否已存在
        FileEo existingFile = null;
        boolean isReplacing = false;
        try {
            existingFile = fileRepository.getFileById(StringId.of(fileId));
        } catch (Exception e) {
            // 文件不存在，继续
            log.debug("文件ID: {} 不存在，将创建新文件", fileId);
        }

        // 4. 如果文件已存在，处理替换逻辑
        if (existingFile != null) {
            if (!cmd.getReplace()) {
                throw new BizException("文件ID已存在: " + fileId + "，如需替换请设置replace参数为true");
            }

            // 5. 删除FTP上的旧文件
            try {
                String oldPath = existingFile.getPath().getValue();
                // 从完整路径中提取目录路径（去掉基础路径前缀）
                String relativePath = extractRelativePath(oldPath);
                boolean deleted = FtpUtil.deleteFile(fileId, relativePath);
                if (!deleted) {
                    log.warn("删除旧文件失败，文件可能不存在: {}/{}", relativePath, fileId);
                }
            } catch (IOException e) {
                log.error("删除FTP文件失败", e);
                throw new BizException("删除旧文件失败: " + e.getMessage());
            }
            isReplacing = true;
        }

        // 6. 上传新文件到FTP
        String filePath;
        try {
            // 以日期为目录 (yyyyMMdd格式)
            String dateFolder = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            filePath = FtpUtil.uploadFile(dateFolder, fileId, cmd.getFile().getInputStream());
        } catch (IOException e) {
            log.error("上传文件到FTP失败", e);
            throw new BizException("上传服务器异常: " + e.getMessage());
        }

        // 7. 保存或更新数据库记录
        if (StringUtils.isEmpty(filePath)) {
            throw new BizException("文件路径为空，上传失败");
        }

        LocalDateTime now = LocalDateTime.now();
        FileEo fileEo = FileEo.builder()
                .pkId(StringId.of(fileId))
                .path(SafeText.of(filePath))
                .fileName(SafeText.of(cmd.getFile().getOriginalFilename()))
                .createTime(now)
                .build();

        int result;
        if (isReplacing) {
            result = fileRepository.updateFile(fileEo);
            AssertUtil.assertTrue(result == 1, "更新文件信息失败");
        } else {
            result = fileRepository.addFile(fileEo);
            AssertUtil.assertTrue(result == 1, "保存文件信息失败");
        }

        // 8. 返回结果
        return UploadFileResult.of(
                fileId,
                cmd.getFile().getOriginalFilename(),
                isReplacing,
                now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    /**
     * 从完整FTP路径中提取相对路径
     * 例如: /home/ftpUser/cloudFile/20250101 -> 20250101
     */
    private String extractRelativePath(String fullPath) {
        if (StringUtils.isEmpty(fullPath)) {
            return "";
        }
        // 假设路径格式为: /home/ftpUser/cloudFile/20250101
        // 我们需要提取最后一个目录部分
        String[] parts = fullPath.split("/");
        if (parts.length > 0) {
            return parts[parts.length - 1];
        }
        return fullPath;
    }
}
