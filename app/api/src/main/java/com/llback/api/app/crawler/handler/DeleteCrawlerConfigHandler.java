package com.llback.api.app.crawler.handler;

import com.llback.api.app.crawler.dto.req.DeleteCrawlerConfigCmd;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.crawler.repository.CrawlerConfigRepository;
import com.llback.core.crawler.service.CrawlerSchedulerService;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 删除爬虫配置Handler
 *
 * @author llback
 */
@Component
@HandlerAcl("crawler:config:delete")
public class DeleteCrawlerConfigHandler implements Handler<Void, DeleteCrawlerConfigCmd> {

    @Autowired
    private CrawlerConfigRepository crawlerConfigRepository;

    @Autowired
    private CrawlerSchedulerService crawlerSchedulerService;

    @Override
    public Void execute(DeleteCrawlerConfigCmd cmd) {
        AssertUtil.notEmpty(cmd.getPkId(), "配置ID不能为空");
        crawlerConfigRepository.deleteById(StringId.of(cmd.getPkId()));
        // 取消调度
        crawlerSchedulerService.unschedule(cmd.getPkId());
        return null;
    }
}
