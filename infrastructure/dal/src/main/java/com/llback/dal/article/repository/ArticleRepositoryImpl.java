package com.llback.dal.article.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.llback.api.app.article.dto.resp.ArticleContentResp;
import com.llback.api.app.article.fetch.ArticleFetch;
import com.llback.common.types.StringId;
import com.llback.core.article.eo.ArticleEo;
import com.llback.core.article.repository.ArticleRepository;
import com.llback.dal.article.dao.ArticleDao;
import com.llback.dal.article.po.ArticleContentPo;
import com.llback.dal.article.po.ArticlePo;
import com.llback.rt.common.cache.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 文章仓储实现
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Component
public class ArticleRepositoryImpl implements ArticleRepository, ArticleFetch {
    /**
     * 文章数据访问接口
     */
    @Autowired
    private ArticleDao articleDao;

    /**
     * 获取文章列表
     *
     * @author yz.sun
     * @date 2025/8/22
     */
    @Override
    public PageInfo<ArticleEo> listArticle(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        PageInfo<ArticlePo> poPageInfo = new PageInfo<>(articleDao.listArticle());
        return PoAssembleUtil.poPage2EoPage(poPageInfo, ArticleEo.class);
    }

    /**
     * 获取文章内容
     *
     * @author yz.sun
     * @date 2025/8/22
     */
    @Override
    public ArticleContentResp queryArticleContent(StringId articleId, String craft) {
        ArticleContentPo articleContentPo = articleDao.getArticleById(articleId.toString(), craft);
        return PoAssembleUtil.poToDto(articleContentPo, ArticleContentResp.class);
    }
}
