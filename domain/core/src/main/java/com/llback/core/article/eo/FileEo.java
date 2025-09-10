package com.llback.core.article.eo;

import com.llback.common.types.SafeText;
import com.llback.common.types.StringId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 文件EO
 *
 * @author yz.sun
 * @date 2025/9/10
 */
@Getter
@Builder
public class FileEo {
    /**
     * 文件ID
     */
    private StringId pkId;

    /**
     * 文件路径
     */
    private SafeText path;

    /**
     * 文件名
     */
    private SafeText fileName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
