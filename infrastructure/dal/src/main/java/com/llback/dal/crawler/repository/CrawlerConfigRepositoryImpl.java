package com.llback.dal.crawler.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.llback.common.types.StringId;
import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.llback.core.crawler.repository.CrawlerConfigRepository;
import com.llback.dal.crawler.dao.CrawlerDao;
import com.llback.dal.crawler.po.CrawlerConfigPo;
import com.llback.rt.common.util.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 爬虫配置仓储实现类
 *
 * @author llback
 */
@Component
public class CrawlerConfigRepositoryImpl implements CrawlerConfigRepository {
    @Autowired
    private CrawlerDao crawlerDao;

    @Override
    public void save(CrawlerConfigEo crawlerConfigEo) {
        CrawlerConfigPo po = PoAssembleUtil.eo2Po(crawlerConfigEo, CrawlerConfigPo.class);
        crawlerDao.insertConfig(po);
    }

    @Override
    public void update(CrawlerConfigEo crawlerConfigEo) {
        CrawlerConfigPo po = PoAssembleUtil.eo2Po(crawlerConfigEo, CrawlerConfigPo.class);
        crawlerDao.updateConfig(po);
    }

    @Override
    public void deleteById(StringId pkId) {
        crawlerDao.deleteConfigById(pkId.toString());
    }

    @Override
    public CrawlerConfigEo findById(StringId pkId) {
        CrawlerConfigPo po = crawlerDao.queryConfigById(pkId.toString());
        return PoAssembleUtil.po2Eo(po, CrawlerConfigEo.class);
    }

    @Override
    public PageInfo<CrawlerConfigEo> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CrawlerConfigPo> poList = crawlerDao.queryAllConfig();
        PageInfo<CrawlerConfigPo> poPage = new PageInfo<>(poList);
        return PoAssembleUtil.poPage2EoPage(poPage, CrawlerConfigEo.class);
    }

    @Override
    public List<CrawlerConfigEo> findByEnabled(boolean enabled) {
        List<CrawlerConfigPo> poList = crawlerDao.queryConfigByEnabled(enabled);
        return PoAssembleUtil.poList2EoList(poList, CrawlerConfigEo.class);
    }
}
