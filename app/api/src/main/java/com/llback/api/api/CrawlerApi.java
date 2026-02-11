package com.llback.api.api;

import com.llback.api.app.crawler.dto.req.*;
import com.llback.frame.rest.RestApi;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.annotation.*;

/**
 * 爬虫管理接口
 *
 * @author llback
 */
@RestController
@RequestMapping("/crawler")
public class CrawlerApi implements RestApi {

    /**
     * 获取爬虫配置列表
     */
    @GetMapping("/config/list")
    public RestResult getCrawlerConfigList(QueryCrawlerConfigListReq req) {
        return this.execute(req);
    }

    /**
     * 新增爬虫配置
     */
    @PostMapping("/config/add")
    public RestResult addCrawlerConfig(@RequestBody AddCrawlerConfigCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 更新爬虫配置
     */
    @PostMapping("/config/update")
    public RestResult updateCrawlerConfig(@RequestBody UpdateCrawlerConfigCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 删除爬虫配置
     */
    @PostMapping("/config/delete")
    public RestResult deleteCrawlerConfig(@RequestBody DeleteCrawlerConfigCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 执行爬虫
     */
    @PostMapping("/execute")
    public RestResult executeCrawler(@RequestBody ExecuteCrawlerCmd cmd) {
        return this.execute(cmd);
    }

    /**
     * 获取爬取记录列表
     */
    @GetMapping("/record/list")
    public RestResult getCrawlerRecordList(QueryCrawlerRecordListReq req) {
        return this.execute(req);
    }
}
