package com.llback.core.crawler.repository;

import com.llback.common.types.StringId;
import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.github.pagehelper.PageInfo;

/**
 * 爬虫配置仓储接口
 *
 * @author llback
 */
public interface CrawlerConfigRepository {
    /**
     * 保存爬虫配置
     *
     * @param crawlerConfigEo 爬虫配置实体
     */
    void save(CrawlerConfigEo crawlerConfigEo);

    /**
     * 更新爬虫配置
     *
     * @param crawlerConfigEo 爬虫配置实体
     */
    void update(CrawlerConfigEo crawlerConfigEo);

    /**
     * 根据ID删除配置
     *
     * @param pkId 主键ID
     */
    void deleteById(StringId pkId);

    /**
     * 根据ID查询配置
     *
     * @param pkId 主键ID
     * @return 爬虫配置实体
     */
    CrawlerConfigEo findById(StringId pkId);

    /**
     * 分页查询所有配置
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageInfo<CrawlerConfigEo> findAll(int pageNum, int pageSize);

    /**
     * 根据启用状态查询配置
     *
     * @param enabled 是否启用
     * @return 配置列表
     */
    java.util.List<CrawlerConfigEo> findByEnabled(boolean enabled);
}
