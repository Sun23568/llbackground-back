package com.llback.api.app.crawler.handler;

import com.github.pagehelper.PageInfo;
import com.llback.api.app.crawler.dto.CrawlerRecordDto;
import com.llback.api.app.crawler.dto.req.QueryCrawlerRecordListReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.common.types.StringId;
import com.llback.core.crawler.eo.CrawlerRecordEo;
import com.llback.core.crawler.repository.CrawlerRecordRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询爬取记录列表Handler
 *
 * @author llback
 */
@Component
@HandlerAcl("crawler:record:list")
public class QueryCrawlerRecordListHandler implements Handler<Map<String, Object>, QueryCrawlerRecordListReq> {

    @Autowired
    private CrawlerRecordRepository crawlerRecordRepository;

    @Override
    public Map<String, Object> execute(QueryCrawlerRecordListReq req) {
        PageInfo<CrawlerRecordEo> eoPage;

        // 根据是否有configId选择查询方式
        if (req.getConfigId() != null && !req.getConfigId().isEmpty()) {
            eoPage = crawlerRecordRepository.findByConfigId(
                    StringId.of(req.getConfigId()),
                    req.getPageNum(),
                    req.getPageRow());
        } else {
            eoPage = crawlerRecordRepository.findAll(
                    req.getPageNum(),
                    req.getPageRow());
        }

        // 转换为DTO
        List<CrawlerRecordDto> dtoList = DtoEoAssemblerUtil.eoList2DtoList(eoPage.getList(), CrawlerRecordDto.class);

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("list", dtoList);
        result.put("total", eoPage.getTotal());

        return result;
    }
}
