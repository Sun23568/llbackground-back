package com.llback.dal.crawler.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.llback.common.types.StringId;
import com.llback.core.crawler.eo.CrawlerRecordEo;
import com.llback.core.crawler.repository.CrawlerRecordRepository;
import com.llback.dal.crawler.dao.CrawlerDao;
import com.llback.dal.crawler.po.CrawlerRecordPo;
import com.llback.rt.common.util.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 爬取记录仓储实现类
 *
 * @author llback
 */
@Component
public class CrawlerRecordRepositoryImpl implements CrawlerRecordRepository {
    @Autowired
    private CrawlerDao crawlerDao;

    @Override
    public void save(CrawlerRecordEo crawlerRecordEo) {
        CrawlerRecordPo po = PoAssembleUtil.eo2Po(crawlerRecordEo, CrawlerRecordPo.class);
        crawlerDao.insertRecord(po);
    }

    @Override
    public PageInfo<CrawlerRecordEo> findByConfigId(StringId configId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CrawlerRecordPo> poList = crawlerDao.queryRecordByConfigId(configId.toString());
        PageInfo<CrawlerRecordPo> poPage = new PageInfo<>(poList);
        return PoAssembleUtil.poPage2EoPage(poPage, CrawlerRecordEo.class);
    }

    @Override
    public PageInfo<CrawlerRecordEo> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CrawlerRecordPo> poList = crawlerDao.queryAllRecord();
        PageInfo<CrawlerRecordPo> poPage = new PageInfo<>(poList);
        return PoAssembleUtil.poPage2EoPage(poPage, CrawlerRecordEo.class);
    }
}
