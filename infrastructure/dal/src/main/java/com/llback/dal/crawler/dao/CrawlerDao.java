package com.llback.dal.crawler.dao;

import com.llback.dal.crawler.po.CrawlerConfigPo;
import com.llback.dal.crawler.po.CrawlerRecordPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 爬虫模块DAO
 *
 * @author llback
 */
@Mapper
public interface CrawlerDao {
    /**
     * 新增爬虫配置
     *
     * @param crawlerConfigPo 爬虫配置
     */
    void insertConfig(CrawlerConfigPo crawlerConfigPo);

    /**
     * 更新爬虫配置
     *
     * @param crawlerConfigPo 爬虫配置
     */
    void updateConfig(CrawlerConfigPo crawlerConfigPo);

    /**
     * 根据ID删除配置
     *
     * @param pkId 主键ID
     */
    void deleteConfigById(@Param("pkId") String pkId);

    /**
     * 根据ID查询配置
     *
     * @param pkId 主键ID
     * @return 爬虫配置
     */
    CrawlerConfigPo queryConfigById(@Param("pkId") String pkId);

    /**
     * 查询所有配置
     *
     * @return 配置列表
     */
    List<CrawlerConfigPo> queryAllConfig();

    /**
     * 根据启用状态查询配置
     *
     * @param enabled 是否启用
     * @return 配置列表
     */
    List<CrawlerConfigPo> queryConfigByEnabled(@Param("enabled") boolean enabled);

    /**
     * 新增爬取记录
     *
     * @param crawlerRecordPo 爬取记录
     */
    void insertRecord(CrawlerRecordPo crawlerRecordPo);

    /**
     * 根据配置ID查询记录
     *
     * @param configId 配置ID
     * @return 记录列表
     */
    List<CrawlerRecordPo> queryRecordByConfigId(@Param("configId") String configId);

    /**
     * 查询所有记录
     *
     * @return 记录列表
     */
    List<CrawlerRecordPo> queryAllRecord();

    /**
     * 查询指定配置最新一条成功记录
     *
     * @param configId 配置ID
     * @return 最新成功记录，无则返回 null
     */
    CrawlerRecordPo queryLatestSuccessRecord(@Param("configId") String configId);
}
