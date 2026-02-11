package com.llback.api.app.crawler.handler;

import com.github.pagehelper.PageInfo;
import com.llback.api.app.crawler.dto.CrawlerConfigDto;
import com.llback.api.app.crawler.dto.req.QueryCrawlerConfigListReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.llback.core.crawler.repository.CrawlerConfigRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询爬虫配置列表Handler
 *
 * @author llback
 */
@Component
@HandlerAcl("crawler:config:list")
public class QueryCrawlerConfigListHandler implements Handler<Map<String, Object>, QueryCrawlerConfigListReq> {

    @Autowired
    private CrawlerConfigRepository crawlerConfigRepository;

    @Override
    public Map<String, Object> execute(QueryCrawlerConfigListReq req) {
        // 查询分页数据
        PageInfo<CrawlerConfigEo> eoPage = crawlerConfigRepository.findAll(
                req.getPageNum(),
                req.getPageRow());

        // 转换为DTO
        List<CrawlerConfigDto> dtoList = DtoEoAssemblerUtil.eoList2DtoList(eoPage.getList(), CrawlerConfigDto.class);

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("list", dtoList);
        result.put("total", eoPage.getTotal());

        return result;
    }
}
