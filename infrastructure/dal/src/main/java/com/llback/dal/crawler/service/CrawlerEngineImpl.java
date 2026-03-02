package com.llback.dal.crawler.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.llback.core.crawler.service.CrawlerEngine;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 爬虫引擎实现
 *
 * @author llback
 */
@Slf4j
@Service
public class CrawlerEngineImpl implements CrawlerEngine {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(\\w+)\\}\\}");

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public String execute(CrawlerConfigEo config, Map<String, String> variables) {
        // 替换占位符
        String url = replacePlaceholders(config.getTargetUrl(), variables);
        String method = config.getRequestMethod();
        String headersJson = config.getRequestHeaders();
        String bodyContent = replacePlaceholders(config.getRequestBody(), variables);

        log.info("开始执行爬虫任务: {} {}", method, url);
        if (variables != null && !variables.isEmpty()) {
            log.info("执行变量: {}", variables);
        }

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
            // 应用后置处理器
            return applyPostProcessor(result, config.getPostProcessor());
        } catch (IOException e) {
            log.error("爬虫执行异常: {}", url, e);
            throw new RuntimeException("爬虫执行异常: " + e.getMessage(), e);
        }
    }

    /**
     * 应用后置处理器，对原始响应进行字段提取
     *
     * <p>
     * postProcessor JSON 格式：
     * 
     * <pre>
     * {
     *   "type": "jsonExtract",
     *   "fields": {
     *     "日期": "data.todayRecord[0].date",
     *     "题号": "data.todayRecord[0].question.questionFrontendId"
     *   }
     * }
     * </pre>
     *
     * @param rawResult         HTTP 原始响应体
     * @param postProcessorJson 后置处理器配置 JSON
     * @return 处理后的结果（提取失败时原样返回）
     */
    private String applyPostProcessor(String rawResult, String postProcessorJson) {
        if (postProcessorJson == null || postProcessorJson.trim().isEmpty()) {
            return rawResult;
        }
        try {
            JSONObject config = JSON.parseObject(postProcessorJson);
            String type = config.getString("type");
            if ("jsonExtract".equals(type)) {
                JSONObject fields = config.getJSONObject("fields");
                if (fields == null || fields.isEmpty()) {
                    return rawResult;
                }
                Object root = JSON.parse(rawResult);
                JSONObject extracted = new JSONObject(new LinkedHashMap<>()); // 保持插入顺序
                for (String alias : fields.keySet()) {
                    String path = fields.getString(alias);
                    Object value = getByPath(root, path);
                    extracted.put(alias, value);
                }
                log.info("后置处理器执行成功，提取字段: {}", fields.keySet());
                return extracted.toJSONString();
            }
        } catch (Exception e) {
            log.warn("后置处理器执行失败，返回原始数据: {}", e.getMessage());
        }
        return rawResult;
    }

    /**
     * 按点分隔路径（支持数组下标，如 "data.items[0].name"）从 JSON 中取值
     *
     * @param node JSON 根节点
     * @param path 路径字符串
     * @return 对应路径的值，路径不存在时返回 null
     */
    private Object getByPath(Object node, String path) {
        if (node == null || path == null || path.trim().isEmpty()) {
            return null;
        }
        String[] parts = path.split("\\.");
        Object current = node;
        for (String part : parts) {
            if (current == null)
                return null;
            if (part.contains("[")) {
                // 处理数组下标，如 "todayRecord[0]"
                String key = part.substring(0, part.indexOf('['));
                int idx = Integer.parseInt(part.replaceAll(".*\\[(\\d+)\\].*", "$1"));
                if (!key.isEmpty() && current instanceof JSONObject) {
                    current = ((JSONObject) current).get(key);
                }
                if (current instanceof com.alibaba.fastjson2.JSONArray) {
                    current = ((com.alibaba.fastjson2.JSONArray) current).get(idx);
                }
            } else {
                if (current instanceof JSONObject) {
                    current = ((JSONObject) current).get(part);
                } else {
                    return null;
                }
            }
        }
        return current;
    }

    /**
     * 替换模板字符串中的 {{占位符}}
     *
     * @param template  包含占位符的字符串
     * @param variables 占位符变量 Map
     * @return 替换后的字符串
     */
    private String replacePlaceholders(String template, Map<String, String> variables) {
        if (template == null || template.isEmpty() || variables == null || variables.isEmpty()) {
            return template;
        }
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = variables.getOrDefault(key, matcher.group(0)); // 未提供则保留原占位符
            matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
