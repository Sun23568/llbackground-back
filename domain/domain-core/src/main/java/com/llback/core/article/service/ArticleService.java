package com.llback.core.article.service;

/**
 * 文章服务
 */
public interface ArticleService {
    /**
     * 处理HTML中的图片
     * 将图片上传至FTP，并且返回带有图片URL的HTML
     */
    String handlerHtmlImage(String html);
}
