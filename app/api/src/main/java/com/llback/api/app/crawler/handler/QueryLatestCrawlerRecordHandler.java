package com.llback.api.app.crawler.handler;

import com.llback.api.app.crawler.dto.CrawlerRecordDto;
import com.llback.api.app.crawler.dto.req.QueryLatestCrawlerRecordReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.crawler.eo.CrawlerRecordEo;
import com.llback.core.crawler.repository.CrawlerRecordRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询最新成功爬取记录Handler
 *
 * @author llback
 */
@Component
@HandlerAcl("crawler:record:latest")
public class QueryLatestCrawlerRecordHandler implements Handler<CrawlerRecordDto, QueryLatestCrawlerRecordReq> {

    @Autowired
    private CrawlerRecordRepository crawlerRecordRepository;

    @Override
    public CrawlerRecordDto execute(QueryLatestCrawlerRecordReq req) {
        AssertUtil.notEmpty(req.getConfigId(), "配置ID不能为空");
        CrawlerRecordEo eo = crawlerRecordRepository.findLatestSuccess(StringId.of(req.getConfigId()));
        return eo != null ? DtoEoAssemblerUtil.eo2Dto(eo, CrawlerRecordDto.class) : null;
    }
}
