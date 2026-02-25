package com.llback.api.app.crawler.handler;

import com.llback.api.app.crawler.dto.req.AddCrawlerConfigCmd;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.llback.core.crawler.repository.CrawlerConfigRepository;
import com.llback.core.crawler.service.CrawlerSchedulerService;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 添加爬虫配置Handler
 *
 * @author llback
 */
@Component
@HandlerAcl("crawler:config:add")
public class AddCrawlerConfigHandler implements Handler<Void, AddCrawlerConfigCmd> {

    @Autowired
    private CrawlerConfigRepository crawlerConfigRepository;

    @Autowired
    private CrawlerSchedulerService crawlerSchedulerService;

    @Override
    public Void execute(AddCrawlerConfigCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getConfigName(), "配置名称不能为空");
        AssertUtil.notEmpty(cmd.getTargetUrl(), "目标URL不能为空");

        // 构建爬虫配置实体
        CrawlerConfigEo configEo = CrawlerConfigEo.builder()
                .pkId(StringId.of(RandomIdUtil.uuid()))
                .configName(cmd.getConfigName())
                .targetUrl(cmd.getTargetUrl())
                .requestMethod(cmd.getRequestMethod())
                .requestHeaders(cmd.getRequestHeaders())
                .requestBody(cmd.getRequestBody())
                .preProcessor(cmd.getPreProcessor())
                .postProcessor(cmd.getPostProcessor())
                .cronExpression(cmd.getCronExpression())
                .enabled(cmd.getEnabled() != null ? cmd.getEnabled() : true)
                .description(cmd.getDescription())
                .createTime(LocalDateTime.now())
                .build();

        // 保存配置
        crawlerConfigRepository.save(configEo);

        // 调度任务
        crawlerSchedulerService.schedule(configEo);

        return null;
    }
}
