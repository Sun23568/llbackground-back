package com.llback.api.app.crawler.handler;

import com.llback.api.app.crawler.dto.req.ExecuteCrawlerCmd;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.llback.core.crawler.eo.CrawlerRecordEo;
import com.llback.core.crawler.types.CrawlerStatus;
import com.llback.core.crawler.repository.CrawlerConfigRepository;
import com.llback.core.crawler.repository.CrawlerRecordRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 执行爬虫Handler
 *
 * @author llback
 */
@Component
@HandlerAcl("crawler:execute")
public class ExecuteCrawlerHandler implements Handler<Void, ExecuteCrawlerCmd> {

    @Autowired
    private CrawlerConfigRepository crawlerConfigRepository;

    @Autowired
    private CrawlerRecordRepository crawlerRecordRepository;

    @Override
    public Void execute(ExecuteCrawlerCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getConfigId(), "配置ID不能为空");

        // 查询配置
        CrawlerConfigEo config = crawlerConfigRepository.findById(StringId.of(cmd.getConfigId()));
        AssertUtil.notNull(config, "爬虫配置不存在");
        // 检查配置是否启用
        if (!config.getEnabled()) {
            throw new IllegalArgumentException("爬虫配置未启用");
        }

        long startTime = System.currentTimeMillis();
        String resultData = null;
        String errorMessage = null;
        CrawlerStatus status;

        try {
            // TODO:
            // 这里先返回一个模拟结果
            resultData = executeCrawler(config);
            status = CrawlerStatus.SUCCESS;
        } catch (Exception e) {
            status = CrawlerStatus.FAILED;
            errorMessage = e.getMessage();
        }

        long duration = System.currentTimeMillis() - startTime;

        // 保存执行记录
        CrawlerRecordEo record = CrawlerRecordEo.builder()
                .pkId(StringId.of(RandomIdUtil.uuid()))
                .configId(config.getPkId())
                .configName(config.getConfigName())
                .executeTime(LocalDateTime.now())
                .status(status)
                .resultData(resultData)
                .errorMessage(errorMessage)
                .duration(duration)
                .build();

        crawlerRecordRepository.save(record);

        return null;
    }

    /**
     * 执行爬虫（简单实现）
     * TODO: 实现完整的爬虫逻辑，包括HTTP请求、前置/后置处理器等
     */
    private String executeCrawler(CrawlerConfigEo config) {
        // 模拟爬取结果
        return String.format("{\"url\":\"%s\",\"method\":\"%s\",\"timestamp\":\"%s\",\"data\":\"模拟爬取的数据内容\"}",
                config.getTargetUrl(),
                config.getRequestMethod(),
                LocalDateTime.now());
    }
}
