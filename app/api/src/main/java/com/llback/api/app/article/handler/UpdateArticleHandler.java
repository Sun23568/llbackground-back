package com.llback.api.app.article.handler;

import com.llback.api.app.article.dto.ArticleDto;
import com.llback.api.app.article.dto.req.UpdateArticleCmd;
import com.llback.common.constants.PublicFlagEnum;
import com.llback.common.types.ArticleTitle;
import com.llback.common.types.Flag;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.article.eo.ArticleContentEo;
import com.llback.core.article.eo.ArticleEo;
import com.llback.core.article.repository.ArticleRepository;
import com.llback.core.article.service.ArticleService;
import com.llback.frame.Handler;
import com.llback.frame.context.ReqContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 修改文章处理器
 */
@Component
public class UpdateArticleHandler implements Handler<ArticleDto, UpdateArticleCmd> {

    /**
     * 文章仓库
     */
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 文章领域
     */
    @Autowired
    private ArticleService articleService;

    @Override
    @Transactional
    public ArticleDto execute(UpdateArticleCmd req) {
        AssertUtil.notEmpty(req.getTitle(), "标题不能为空");
        AssertUtil.notEmpty(req.getContent(), "内容不能为空");
        AssertUtil.notEmpty(req.getPublicFlag(), "公开标识不可为空");
        AssertUtil.notEmpty(req.getDraft(), "入参异常");

        // 构建EO
        ArticleEo articleEo = ArticleEo.builder()
                .author(ReqContext.getCurrent().getUserSession().getUserId())
                .title(ArticleTitle.of(req.getTitle()))
                .publicFlag(Flag.of(req.getPublicFlag()))
                .build();
        // 非草稿时初始化创建时间、更新时间
        if (PublicFlagEnum.NO_FLAG.getCode().equals(req.getDraft())) {
            articleEo.updateUpdateTime();
        }

        String articleId = req.getArticleId();
        // 第一次新增
        if (StringUtils.isEmpty(req.getArticleId())) {
            articleEo.initPkId();
            articleEo.initCreateTime();
            articleId = articleEo.getPkId().toString();
            AssertUtil.assertTrue(articleRepository.addArticle(articleEo) == 1, "添加文章失败");
        } else {
            articleEo.setPkId(StringId.of(articleId));
            AssertUtil.assertTrue(articleRepository.updateArticle(articleEo) == 1, "更新文章失败");
        }
        // 删除草稿信息
        articleRepository.removeContent(StringId.of(articleId), PublicFlagEnum.YES_FLAG.getCode().equals(req.getDraft()));
        // 转换内容
        String contentAfterHandler = articleService.handlerHtmlImage(req.getContent());
        // 构造 文章内容实例
        ArticleContentEo articleContentEo = ArticleContentEo.builder()
                .pkId(StringId.of(RandomIdUtil.uuid()))
                .articleId(StringId.of(articleId))
                .draft(Flag.of(req.getDraft()))
                .content(req.getContent())
                .createTime(articleEo.getCreateTime())
                .build();
        articleRepository.addContent(articleContentEo);

        return ArticleDto.builder()
                .pkId(articleId)
                .createTime(articleEo.getCreateTime())
                .build();
    }
}
