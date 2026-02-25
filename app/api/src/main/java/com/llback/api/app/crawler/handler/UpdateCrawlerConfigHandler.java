package com.llback.api.app.crawler.handler;

import com.llback.api.app.crawler.dto.req.UpdateCrawlerConfigCmd;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.llback.core.crawler.repository.CrawlerConfigRepository;
import com.llback.core.crawler.service.CrawlerSchedulerService;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新爬虫配置Handler
 *
 * @author llback
 */
@Component
@HandlerAcl("crawler:config:update")
public class UpdateCrawlerConfigHandler implements Handler<Void, UpdateCrawlerConfigCmd> {

    @Autowired
    private CrawlerConfigRepository crawlerConfigRepository;

    @Autowired
    private CrawlerSchedulerService crawlerSchedulerService;

    @Override
    public Void execute(UpdateCrawlerConfigCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getPkId(), "配置ID不能为空");

        // 构建爬虫配置实体
        CrawlerConfigEo configEo = CrawlerConfigEo.builder()
                .pkId(StringId.of(cmd.getPkId()))
                .configName(cmd.getConfigName())
                .targetUrl(cmd.getTargetUrl())
                .requestMethod(cmd.getRequestMethod())
                .requestHeaders(cmd.getRequestHeaders())
                .requestBody(cmd.getRequestBody())
                .preProcessor(cmd.getPreProcessor())
                .postProcessor(cmd.getPostProcessor())
                .cronExpression(cmd.getCronExpression())
                .enabled(cmd.getEnabled())
                .description(cmd.getDescription())
                .build();

        // 更新配置
        crawlerConfigRepository.update(configEo);

        // 重新调度
        crawlerSchedulerService.reschedule(configEo);

        return null;
    }
}
