package com.llback.core.crawler.types;

import com.llback.common.types.SafeText;
import com.llback.common.types.StringId;

import java.io.Serializable;

/**
 * 爬虫执行状态
 *
 * @author llback
 */
public final class CrawlerStatus extends SafeText implements Serializable {

    /**
     * 执行成功
     */
    public static final CrawlerStatus SUCCESS = new CrawlerStatus("SUCCESS");

    /**
     * 执行失败
     */
    public static final CrawlerStatus FAILED = new CrawlerStatus("FAILED");

    /**
     * 构造函数
     *
     * @param status 状态值
     */
    private CrawlerStatus(CharSequence status) {
        super(status, 20, "执行状态");
    }

    /**
     * 构造函数(空值)
     */
    private CrawlerStatus() {
        super();
    }

    /**
     * 构造函数
     *
     * @param status 状态值
     * @return 状态对象
     */
    public static CrawlerStatus of(CharSequence status) {
        if (StringId.isEmpty(status)) {
            return null;
        }
        String statusStr = status.toString();
        if (SUCCESS.toString().equals(statusStr)) {
            return SUCCESS;
        }
        if (FAILED.toString().equals(statusStr)) {
            return FAILED;
        }
        return new CrawlerStatus(status);
    }
}
