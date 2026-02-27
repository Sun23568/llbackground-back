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
import com.llback.core.crawler.service.CrawlerEngine;
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

    @Autowired
    private CrawlerEngine crawlerEngine;

    @Override
    public Void execute(ExecuteCrawlerCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getConfigId(), "配置ID不能为空");

        // 查询配置
        CrawlerConfigEo config = crawlerConfigRepository.findById(StringId.of(cmd.getConfigId()));
        AssertUtil.notNull(config, "爬虫配置不存在");
        long startTime = System.currentTimeMillis();
        String resultData = null;
        String errorMessage = null;
        CrawlerStatus status;

        try {
            // 使用爬虫引擎执行（传入变量，用于替换 URL/Body 中的占位符）
            resultData = crawlerEngine.execute(config, cmd.getVariables());
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
}
