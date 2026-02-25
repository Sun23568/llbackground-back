package com.llback.dal.crawler.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.llback.core.crawler.service.CrawlerEngine;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 爬虫引擎实现
 *
 * @author llback
 */
@Slf4j
@Service
public class CrawlerEngineImpl implements CrawlerEngine {

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public String execute(CrawlerConfigEo config) {
        String url = config.getTargetUrl();
        String method = config.getRequestMethod();
        String headersJson = config.getRequestHeaders();
        String bodyContent = config.getRequestBody();

        log.info("开始执行爬虫任务: {} {}", method, url);

        Request.Builder requestBuilder = new Request.Builder().url(url);

        // 设置请求头
        if (headersJson != null && !headersJson.trim().isEmpty()) {
            try {
                JSONObject headers = JSON.parseObject(headersJson);
                for (Map.Entry<String, Object> entry : headers.entrySet()) {
                    if (entry.getValue() != null) {
                        requestBuilder.addHeader(entry.getKey(), entry.getValue().toString());
                    }
                }
            } catch (Exception e) {
                log.error("解析请求头失败: {}", headersJson, e);
            }
        }

        // 设置请求方法和请求体
        if ("GET".equalsIgnoreCase(method)) {
            requestBuilder.get();
        } else if ("POST".equalsIgnoreCase(method)) {
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    bodyContent != null ? bodyContent : "");
            requestBuilder.post(body);
        } else if ("PUT".equalsIgnoreCase(method)) {
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    bodyContent != null ? bodyContent : "");
            requestBuilder.put(body);
        } else if ("DELETE".equalsIgnoreCase(method)) {
            RequestBody body = (bodyContent != null && !bodyContent.isEmpty()) ? RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    bodyContent) : null;
            requestBuilder.delete(body);
        }

        try (Response response = client.newCall(requestBuilder.build()).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP请求执行失败: " + response.code() + " " + response.message());
            }
            ResponseBody responseBody = response.body();
            String result = responseBody != null ? responseBody.string() : "";
            log.info("爬虫任务执行成功: {}", url);
            return result;
        } catch (IOException e) {
            log.error("爬虫执行异常: {}", url, e);
            throw new RuntimeException("爬虫执行异常: " + e.getMessage(), e);
        }
    }
}
