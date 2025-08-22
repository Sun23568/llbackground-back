package com.llback.api.app.article.handler;

import com.github.pagehelper.PageInfo;
import com.llback.api.app.article.dto.ArticleDto;
import com.llback.api.app.article.dto.req.QueryArticleReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.core.article.eo.ArticleEo;
import com.llback.core.article.repository.ArticleRepository;
import com.llback.frame.Handler;
import com.llback.frame.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 文章查询处理器
 *
 * @author yz.sun
 * @date 2025/8/22
 */
@Component
public class QueryArticleHandler implements Handler<PageResult<ArticleDto>, QueryArticleReq> {
    /**
     * 文章仓库
     */
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public PageResult<ArticleDto> execute(QueryArticleReq req) {
        PageInfo<ArticleEo> articleEoPageInfo = articleRepository.listArticle(req.getPageIndex(), req.getPageSize());
        return DtoEoAssemblerUtil.eoPageToDtoResult(articleEoPageInfo, ArticleDto.class);
    }
}
