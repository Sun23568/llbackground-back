package com.llback.dal.article.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.llback.api.app.article.dto.resp.ArticleContentResp;
import com.llback.api.app.article.fetch.ArticleFetch;
import com.llback.common.types.StringId;
import com.llback.common.types.UserId;
import com.llback.core.article.eo.ArticleContentEo;
import com.llback.core.article.vo.ArticleVo;
import com.llback.core.article.repository.ArticleRepository;
import com.llback.dal.article.dao.ArticleDao;
import com.llback.dal.article.po.ArticleContentPo;
import com.llback.dal.article.po.ArticlePo;
import com.llback.rt.common.util.PoAssembleUtil;
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
    public PageInfo<ArticleVo> listArticle(int pageIndex, int pageSize, UserId userId, boolean queryAll) {
        PageHelper.startPage(pageIndex, pageSize);
        PageInfo<ArticlePo> poPageInfo = new PageInfo<>(articleDao.listArticle(userId.toString(), queryAll));
        return PoAssembleUtil.poPage2EoPage(poPageInfo, ArticleVo.class);
    }

    /**
     * 添加文章
     */
    @Override
    public int addArticle(ArticleVo eo) {
        ArticlePo articlePo = PoAssembleUtil.eo2Po(eo, ArticlePo.class);
        return articleDao.addArticle(articlePo);
    }

    @Override
    public int updateArticle(ArticleVo eo) {
        ArticlePo articlePo = PoAssembleUtil.eo2Po(eo, ArticlePo.class);
        return articleDao.updateArticleBaseInfo(articlePo);
    }

    /**
     * 获取文章内容
     *
     * @author yz.sun
     * @date 2025/8/22
     */
    @Override
    public ArticleContentResp queryArticleContent(StringId articleId, String draft) {
        ArticleContentPo articleContentPo = articleDao.getArticleContentById(articleId.toString(), draft);
        return PoAssembleUtil.poToDto(articleContentPo, ArticleContentResp.class);
    }

    /**
     * 删除文章草稿
     */
    @Override
    public void removeContent(StringId articleId, boolean justRemoveDraft) {
        articleDao.deleteDraft(articleId.toString(), justRemoveDraft);
    }

    /**
     * 判断文章是否存在（非草稿）
     */
    @Override
    public boolean hasInst(StringId articleId) {
        return articleDao.hasInst(articleId.toString()) > 0;
    }

    @Override
    public int addContent(ArticleContentEo articleContentEo) {
        ArticleContentPo articleContentPo = PoAssembleUtil.eo2Po(articleContentEo, ArticleContentPo.class);
        return articleDao.addArticleContent(articleContentPo);
    }

    /**
     * 获取文章基本信息
     */
    @Override
    public ArticleVo getArticleBaseInfo(StringId articleId) {
        ArticlePo articleBaseInfo = articleDao.getArticleBaseInfo(articleId.toString());
        return PoAssembleUtil.po2Eo(articleBaseInfo, ArticleVo.class);
    }

    /**
     * 删除文章
     */
    @Override
    public int removeArticle(String articleId) {
        return articleDao.removeArticle(articleId);
    }
}
