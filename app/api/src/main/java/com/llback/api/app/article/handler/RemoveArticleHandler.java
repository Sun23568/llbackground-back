package com.llback.api.app.article.handler;

import com.llback.api.app.article.dto.req.RemoveArticleCmd;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.article.vo.ArticleVo;
import com.llback.core.article.repository.ArticleRepository;
import com.llback.frame.Handler;
import com.llback.frame.context.ReqContext;
import com.llback.frame.context.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 删除文章处理器
 */
@Component
public class RemoveArticleHandler implements Handler<Void, RemoveArticleCmd> {
    /**
     * 文章仓库
     */
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Void execute(RemoveArticleCmd req) {
        AssertUtil.notEmpty(req.getArticleId(), "文章ID不能为空");

        // 查询该文章信息
        ArticleVo articleBaseInfo = articleRepository.getArticleBaseInfo(StringId.of(req.getArticleId()));
        // 只有作者本人或拥有article:remove权限的人可删除
        UserSession userSession = ReqContext.getCurrent().getUserSession();
        AssertUtil.assertTrue(articleBaseInfo.getAuthor().equals(userSession.getUserId())
                || userSession.hasPerm("article:remove"), "您没有权限删除此文章");
        // 删除文章
        articleRepository.removeContent(StringId.of(req.getArticleId()), true);
        AssertUtil.assertTrue(articleRepository.removeArticle(req.getArticleId()) == 1, "删除异常");
        return Void.TYPE.cast(null);
    }
}
