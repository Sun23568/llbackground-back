package com.llback.core.article.service.impl;

import com.llback.core.article.feign.FtpFileFeign;
import com.llback.core.article.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.function.Function;

/**
 * 文章服务实现类
 */
@Component
public class ArticleServiceImpl implements ArticleService {
    /**
     * ftp文件服务
     */
    @Lazy
    @Autowired
    private FtpFileFeign ftpFileFeign;

    @Value("${ftp.service.get-image-prefix}")
    private String ftpGetImageUrl;

    @Override
    public String handlerHtmlImage(String html) {
        return handlerHtmlImage(html, this::uploadImage);
    }

    /**
     * 处理html中的图片
     */
    public String handlerHtmlImage(String html, Function<Element, String> function) {
        if (StringUtils.isEmpty(html)) {
            return html;
        }
        // 解析HTML字符串
        Document parse = Jsoup.parse(html);
        // 选择元素
        Elements imgBase64Elements = parse.select("img[src]");
        // 获取元素属性
        imgBase64Elements.forEach(imgBase64Element -> imgBase64Element.attr("src", function.apply(imgBase64Element)));
        parse.outputSettings(new Document.OutputSettings().prettyPrint(false));
        return parse.html();
    }

    /**
     * 上传图片
     */
    private String uploadImage(Element imgBase64Element) {
        String src = imgBase64Element.attr("src");
        if (src.startsWith("data:image")) {
            // 获取图片数据
            String base64Data = src.split(",")[1];
            // 解码图片数据
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            // 创建MultipartFile对象
            MultipartFile multipartFile = new MockMultipartFile("file", "file.png", "image/png", decodedBytes);

            // 上传图片
            ResponseEntity<String> response = ftpFileFeign.uploadFile(multipartFile);
            return response.getBody();
        } else if (src.startsWith(ftpGetImageUrl)) {
            return src.replace(ftpGetImageUrl, "");
        }

        return src;
    }

    /**
     * 处理HTML中的图片
     * 将图片的ID转为URL
     */
    @Override
    public String handlerHtmlImageToUrl(String html) {
        return this.handlerHtmlImage(html, element -> ftpGetImageUrl + element.attr("src"));
    }
}
