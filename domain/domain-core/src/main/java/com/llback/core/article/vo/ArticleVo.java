package com.llback.core.article.vo;

import com.llback.common.types.*;
import com.llback.common.util.RandomIdUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 文章EO
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Getter
@Builder
public class ArticleVo {
    /**
     * 主键
     */
    private StringId pkId;

    /**
     * 标题
     */
    private ArticleTitle title;

    /**
     * 作者
     */
    private UserId author;

    /**
     * 作者名称
     */
    private UserName authorName;

    /**
     * 公开标识
     */
    private Flag publicFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 初始化主键
     */
    public void initPkId() {
        if (pkId == null) {
            pkId = StringId.of(RandomIdUtil.uuid());
        }
    }

    /**
     * 初始化创建时间
     */
    public void initCreateTime() {
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
    }

    /**
     * 初始化创建信息
     */
    public void initCreateInfo(){
        initPkId();
        initCreateTime();
    }

    /**
     * 更新修改时间
     */
    public void updateUpdateTime() {
        updateTime = LocalDateTime.now();
    }

    public void setPkId(StringId pkid) {
        this.pkId = pkid;
    }
}
