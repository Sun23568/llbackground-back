package com.llback.dal.crawler.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.llback.core.crawler.eo.CrawlerConfigEo;
import com.llback.core.crawler.service.CrawlerEngine;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String mailFrom;

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
     * postProcessor JSON 格式（支持单对象或数组链式执行）：
     *
     * <pre>
     * 单对象（旧格式，兼容）:
     * {"type": "jsonExtract", "fields": {...}}
     *
     * 数组（链式，推荐）:
     * [
     *   {"type": "jsonExtract", "fields": {"日期": "data.date", "题名": "data.title"}},
     *   {"type": "sendEmail", "subject": "每日通知", "recipients": ["user@163.com"]}
     * ]
     * </pre>
     *
     * @param rawResult         HTTP 原始响应体
     * @param postProcessorJson 后置处理器配置 JSON（单对象或数组）
     * @return 处理后的结果（提取失败时原样返回）
     */
    private String applyPostProcessor(String rawResult, String postProcessorJson) {
        if (postProcessorJson == null || postProcessorJson.trim().isEmpty()) {
            return rawResult;
        }
        String trimmed = postProcessorJson.trim();
        try {
            if (trimmed.startsWith("[")) {
                // 数组模式：链式执行，每个处理器的输出作为下一个的输入
                JSONArray processors = JSON.parseArray(trimmed);
                String result = rawResult;
                for (int i = 0; i < processors.size(); i++) {
                    JSONObject config = processors.getJSONObject(i);
                    result = applyOneProcessor(result, config);
                }
                return result;
            } else {
                // 单对象模式（向后兼容旧配置）
                JSONObject config = JSON.parseObject(trimmed);
                return applyOneProcessor(rawResult, config);
            }
        } catch (Exception e) {
            log.warn("后置处理器执行失败，返回原始数据: {}", e.getMessage());
        }
        return rawResult;
    }

    /**
     * 执行单个后置处理器，返回处理后的数据
     *
     * @param input  当前数据（上一步输出或原始响应）
     * @param config 单个处理器配置
     * @return 处理后的数据（sendEmail 不修改数据，直接透传）
     */
    private String applyOneProcessor(String input, JSONObject config) {
        String type = config.getString("type");
        if ("jsonExtract".equals(type)) {
            JSONObject fields = config.getJSONObject("fields");
            if (fields == null || fields.isEmpty()) {
                return input;
            }
            Object root = JSON.parse(input);
            JSONObject extracted = new JSONObject(new LinkedHashMap<>());
            for (String alias : fields.keySet()) {
                String path = fields.getString(alias);
                Object value = getByPath(root, path);
                extracted.put(alias, value);
            }
            log.info("后置处理器[jsonExtract]执行成功，提取字段: {}", fields.keySet());
            return extracted.toJSONString();
        } else if ("sendEmail".equals(type)) {
            // 检查发送条件（如果有）
            JSONObject condition = config.getJSONObject("condition");
            if (condition != null && !condition.isEmpty()) {
                if (!checkCondition(input, condition)) {
                    log.info("后置处理器[sendEmail]条件不满足，跳过发送 | 条件: {}", condition);
                    return input;
                }
                log.info("后置处理器[sendEmail]条件满足，准备发送邮件 | 条件: {}", condition);
            }
            sendEmailNotification(config, input);
        } else {
            log.warn("未知的后置处理器类型: {}", type);
        }
        return input;
    }

    /**
     * 检查条件是否满足（支持多规则 AND/OR）
     *
     * <p>
     * condition 格式：
     * 
     * <pre>
     * 多规则: {"logic":"AND","rules":[{"field":"难度","operator":"eq","value":"Hard"},...]}
     * 单条件（兼容）: {"field":"难度","operator":"eq","value":"Hard"}
     * </pre>
     */
    private boolean checkCondition(String dataJson, JSONObject condition) {
        try {
            Object root = JSON.parse(dataJson);
            // 新格式：{logic, rules:[...]}
            JSONArray rules = condition.getJSONArray("rules");
            if (rules != null && !rules.isEmpty()) {
                String logic = condition.getString("logic");
                boolean isAnd = !"OR".equalsIgnoreCase(logic); // 默认 AND
                for (int i = 0; i < rules.size(); i++) {
                    JSONObject rule = rules.getJSONObject(i);
                    boolean result = evalRule(root, rule);
                    if (isAnd && !result) {
                        log.info("条件[AND]第{}条不满足: {}", i + 1, rule);
                        return false;
                    }
                    if (!isAnd && result) {
                        log.info("条件[OR]第{}条满足: {}", i + 1, rule);
                        return true;
                    }
                }
                return isAnd; // AND 全通过→true；OR 全不满足→false
            }
            // 旧格式：单条件向后兼容
            return evalRule(root, condition);
        } catch (Exception e) {
            log.warn("条件判断失败，默认发送: {}", e.getMessage());
            return true;
        }
    }

    /** 评估单条规则 */
    private boolean evalRule(Object root, JSONObject rule) {
        String field = rule.getString("field");
        String operator = rule.getString("operator");
        String expected = rule.getString("value");
        if (field == null || field.trim().isEmpty() || operator == null) {
            return true;
        }
        Object rawValue = getByPath(root, field);
        String actual = rawValue == null ? null : rawValue.toString();
        switch (operator) {
            case "eq":
                return expected != null && expected.equals(actual);
            case "neq":
                return expected == null ? actual != null : !expected.equals(actual);
            case "contains":
                return actual != null && expected != null && actual.contains(expected);
            case "gt":
                return actual != null && expected != null
                        && Double.parseDouble(actual) > Double.parseDouble(expected);
            case "lt":
                return actual != null && expected != null
                        && Double.parseDouble(actual) < Double.parseDouble(expected);
            case "notEmpty":
                return actual != null && !actual.trim().isEmpty();
            case "isEmpty":
                return actual == null || actual.trim().isEmpty();
            default:
                log.warn("未知的条件运算符: {}", operator);
                return true;
        }
    }

    /**
     * 异步发送邮件通知
     *
     * @param config    后置处理器配置（含 recipients、subject）
     * @param rawResult 爬虫响应原始数据（作为邮件正文）
     */
    private void sendEmailNotification(JSONObject config, String rawResult) {
        if (mailSender == null) {
            log.warn("JavaMailSender 未配置，跳过邮件发送");
            return;
        }
        JSONArray recipientsArr = config.getJSONArray("recipients");
        if (recipientsArr == null || recipientsArr.isEmpty()) {
            log.warn("sendEmail 后置处理器：recipients 为空，跳过发送");
            return;
        }
        List<String> recipients = recipientsArr.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        String subject = config.getString("subject");
        if (subject == null || subject.trim().isEmpty()) {
            subject = "爬虫执行结果通知";
        }
        final String finalSubject = subject;
        final String from = mailFrom;
        final String content = buildEmailHtml(rawResult);
        final List<String> to = recipients;

        // 异步发送，不阻塞主流程
        Thread mailThread = new Thread(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setFrom(from.isEmpty() ? "noreply@163.com" : from);
                helper.setTo(to.toArray(new String[0]));
                helper.setSubject(finalSubject);
                helper.setText(content, true);
                mailSender.send(message);
                log.info("邮件通知发送成功，收件人: {}, 主题: {}", to, finalSubject);
            } catch (Exception e) {
                log.warn("邮件通知发送失败，收件人: {}, 原因: {}", to, e.getMessage());
            }
        }, "crawler-mail-sender");
        mailThread.setDaemon(true);
        mailThread.start();
    }

    /**
     * 生成邮件 HTML 内容
     *
     * @param content 爬取结果内容
     * @return HTML 字符串
     */
    private String buildEmailHtml(String content) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // 转换 JSON 为更美观的 HTML 格式
        StringBuilder formattedContent = new StringBuilder();
        try {
            Object parsed = JSON.parse(content);
            if (parsed instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) parsed;
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    formattedContent.append(
                            "<div style='margin-bottom: 12px; padding-bottom: 12px; border-bottom: 1px dashed #ebeef5;'>")
                            .append("<span style='display:inline-block; width: 100px; color: #909399; font-weight: bold;'>")
                            .append(escapeHtml(entry.getKey())).append("：</span>")
                            .append("<span style='color: #303133;'>")
                            .append(escapeHtml(String.valueOf(entry.getValue()))).append("</span>")
                            .append("</div>");
                }
            } else {
                // 如果不是对象，降级为格式化 JSON
                formattedContent.append(
                        "<pre style='background:#f8f9fa;border-radius:6px;padding:16px;font-size:13px;color:#303133;" +
                                "overflow-x:auto;white-space:pre-wrap;word-break:break-all;border-left:4px solid #409EFF'>")
                        .append(escapeHtml(
                                JSON.toJSONString(parsed, com.alibaba.fastjson2.JSONWriter.Feature.PrettyFormat)))
                        .append("</pre>");
            }
        } catch (Exception e) {
            formattedContent.append(
                    "<pre style='background:#f8f9fa;border-radius:6px;padding:16px;font-size:13px;color:#303133;" +
                            "overflow-x:auto;white-space:pre-wrap;word-break:break-all;border-left:4px solid #409EFF'>")
                    .append(escapeHtml(content))
                    .append("</pre>");
        }

        return "<html><body style='font-family:Arial,sans-serif;margin:0;padding:20px;background:#f5f7fa'>" +
                "<div style='max-width:700px;margin:0 auto;background:#fff;border-radius:10px;box-shadow:0 2px 12px rgba(0,0,0,0.08);overflow:hidden'>"
                +
                "<div style='background:linear-gradient(135deg,#409EFF,#67C23A);padding:24px 32px'>" +
                "<h2 style='color:#fff;margin:0;font-size:20px'>🕷️ 爬虫执行结果通知</h2>" +
                "<p style='color:rgba(255,255,255,0.85);margin:8px 0 0 0;font-size:13px'>执行时间：" + time + "</p>" +
                "</div>" +
                "<div style='padding:24px 32px'>" +
                "<p style='color:#606266;font-size:14px;margin-top:0;margin-bottom:20px;'>以下是本次爬虫执行的结果数据：</p>" +
                "<div style='background:#fcfcfc; border: 1px solid #ebeef5; border-radius: 6px; padding: 20px; font-size: 14px;'>"
                +
                formattedContent.toString() +
                "</div>" +
                "</div>" +
                "<div style='padding:12px 32px;background:#f5f7fa;font-size:12px;color:#909399'>" +
                "此邮件由系统自动发送，请勿回复。" +
                "</div></div></body></html>";
    }

    /**
     * HTML 特殊字符转义
     */
    private String escapeHtml(String text) {
        if (text == null)
            return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
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
