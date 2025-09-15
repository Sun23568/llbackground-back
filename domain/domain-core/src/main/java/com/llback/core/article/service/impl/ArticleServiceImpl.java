package com.llback.core.article.service.impl;

import com.llback.core.article.feign.FtpFileFeign;
import com.llback.core.article.repository.ArticleRepository;
import com.llback.core.article.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.Base64;

/**
 * 文章服务实现类
 */
@Component
public class ArticleServiceImpl implements ArticleService {
    /**
     * ftp文件服务
     */
    @Autowired(required = false)
    private FtpFileFeign ftpFileFeign;

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 处理html中的图片
     *
     * @param html
     */
    @Override
    public String handlerHtmlImage(String html) {
        if (StringUtils.isEmpty(html)) {
            return html;
        }
        // 解析HTML字符串
        Document parse = Jsoup.parse(html);
        // 选择元素
        Elements imgBase64Elements = parse.select("img[src]");
        // 获取元素属性
        for (Element imgBase64Element : imgBase64Elements) {
            String src = imgBase64Element.attr("src");
            if (src.startsWith("data:image")) {
                // 获取图片数据
                String base64Data = src.split(",")[1];
                // 解码图片数据
                byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
                // 创建MultipartFile对象
                MultipartFile multipartFile = new MockMultipartFile("file", decodedBytes);
                // 上传图片
                String url = ftpFileFeign.uploadFile(multipartFile).getBody();
                imgBase64Element.attr("src", url);
            }
        }
        return parse.html();
    }

    @PostConstruct
    public void checkFeignClient() {
        if (ftpFileFeign == null) {
            System.out.println("FtpFileFeign 未成功加载");
        } else {
            System.out.println("FtpFileFeign 成功加载");
        }
    }
}
