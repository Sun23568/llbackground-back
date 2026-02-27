package com.llback.dal.crawler.service;

import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.llback.core.crawler.repository.CrawlerConfigRepository;
import com.llback.core.crawler.repository.CrawlerRecordRepository;
import com.llback.core.crawler.service.CrawlerEngine;
import com.llback.core.crawler.service.CrawlerSchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 爬虫调度服务实现
 *
 * @author llback
 */
@Slf4j
@Service
public class CrawlerSchedulerServiceImpl implements CrawlerSchedulerService {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private CrawlerEngine crawlerEngine;

    @Autowired
    private CrawlerRecordRepository crawlerRecordRepository;

    @Autowired
    private CrawlerConfigRepository crawlerConfigRepository;

    /**
     * 存放已调度的任务句柄
     */
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("crawler-task-");
        scheduler.initialize();
        return scheduler;
    }

    /**
     * 应用启动时加载所有已启用的爬虫任务
     */
    @PostConstruct
    public void init() {
        log.info("初始化爬虫定时任务...");
        List<CrawlerConfigEo> enabledConfigs = crawlerConfigRepository.findByEnabled(true);
        for (CrawlerConfigEo config : enabledConfigs) {
            schedule(config);
        }
    }

    @Override
    public void schedule(CrawlerConfigEo config) {
        String configId = config.getPkId().getValue();
        String cron = config.getCronExpression();
        // 如果已经存在，则先取消
        unschedule(configId);

        if (cron == null || cron.trim().isEmpty()) {
            log.warn("爬虫配置 [{}] 未设置 Cron 表达式，跳过调度", config.getConfigName());
            return;
        }

        if (!config.isEnabled()) {
            log.info("爬虫配置 [{}] 未启用，仅取消现有调度", config.getConfigName());
            return;
        }

        try {
            log.info("调度爬虫任务: [{}] Cron: [{}]", config.getConfigName(), cron);
            ScheduledFuture<?> future = taskScheduler.schedule(
                    new CrawlerJob(config, crawlerEngine, crawlerRecordRepository),
                    new CronTrigger(cron));
            scheduledTasks.put(configId, future);
        } catch (Exception e) {
            log.error("调度爬虫任务失败: [{}]", config.getConfigName(), e);
        }
    }

    @Override
    public void unschedule(String configId) {
        ScheduledFuture<?> future = scheduledTasks.remove(configId);
        if (future != null) {
            log.info("取消爬虫任务调度: [{}]", configId);
            future.cancel(true);
        }
    }

    @Override
    public void reschedule(CrawlerConfigEo config) {
        schedule(config);
    }
}
