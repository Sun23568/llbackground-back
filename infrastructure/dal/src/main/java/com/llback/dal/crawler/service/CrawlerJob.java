package com.llback.dal.crawler.service;

import com.llback.common.types.StringId;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.llback.core.crawler.eo.CrawlerRecordEo;
import com.llback.core.crawler.service.CrawlerEngine;
import com.llback.core.crawler.repository.CrawlerRecordRepository;
import com.llback.core.crawler.types.CrawlerStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 爬虫定时任务执行 Job
 *
 * @author llback
 */
@Slf4j
public class CrawlerJob implements Runnable {

    private final CrawlerConfigEo config;
    private final CrawlerEngine crawlerEngine;
    private final CrawlerRecordRepository crawlerRecordRepository;

    public CrawlerJob(CrawlerConfigEo config, CrawlerEngine crawlerEngine,
            CrawlerRecordRepository crawlerRecordRepository) {
        this.config = config;
        this.crawlerEngine = crawlerEngine;
        this.crawlerRecordRepository = crawlerRecordRepository;
    }

    @Override
    public void run() {
        log.info("定时任务触发: 执行爬虫 [{}]", config.getConfigName());

        long startTime = System.currentTimeMillis();
        String resultData = null;
        String errorMessage = null;
        CrawlerStatus status;

        try {
            resultData = crawlerEngine.execute(config);
            status = CrawlerStatus.SUCCESS;
        } catch (Exception e) {
            status = CrawlerStatus.FAILED;
            errorMessage = e.getMessage();
            log.error("爬虫执行失败: {}", config.getConfigName(), e);
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
        log.info("定时任务执行结束: [{}] 状态: {}", config.getConfigName(), status);
    }
}
