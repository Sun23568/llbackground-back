package com.heeexy.example.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dto.OllamaChatDto;
import com.heeexy.example.dto.OllamaChatInfoEntity;
import com.heeexy.example.feign.OllamaClient;
import okhttp3.*;
import okio.BufferedSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ollama服务
 */
@Component
public class OllamaService {

    /**
     * Ollama客户端
     */
    @Autowired
    private OllamaClient ollamaClient;

    @Value("${ollama.url}")
    private String ollamaUrl;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final OkHttpClient client = new OkHttpClient();

    public void generate(OllamaChatDto ollamaChatDto, HttpServletResponse response) {
        String param = generateParams(ollamaChatDto.getChatList(), ollamaChatDto.getModelName());
        RequestBody body = RequestBody.create(param, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(ollamaUrl + "/api/chat").post(body).build();
        executor.execute(() -> {
            PrintWriter writer = null;
            try (Response clientResp = client.newCall(request).execute()) {
                if (!clientResp.isSuccessful()) throw new IOException("Unexpected code " + clientResp);

                // 处理流式响应
                ResponseBody responseBody = clientResp.body();
                // 设置中文
                response.setCharacterEncoding("UTF-8");
                // 修改: 使用 OutputStreamWriter 包装 response.getOutputStream() 并指定 UTF-8 编码
                writer = new PrintWriter(response.getWriter());
                if (responseBody != null) {
                    BufferedSource source = responseBody.source();
                    boolean begin = false;
                    while (!source.exhausted()) {
                        String line = source.readUtf8Line();
                        if (line != null) {
                            // 处理每一行数据
                            String resp = (String) JSONObject.parseObject(line).getJSONObject("message").get("content");
                            if (begin) {
                                if (!StringUtils.isEmpty(resp)) {
                                    writer.write(resp.toCharArray());
                                    writer.flush();
                                }
                            }
                            if ("</think>".equals(resp)) {
                                begin = true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 生成参数
     *
     * @param chatList
     * @param modelName
     * @return
     */
    private String generateParams(List<OllamaChatInfoEntity> chatList, String modelName) {
        JSONObject param = new JSONObject();
        param.put("model", modelName);
        JSONArray questionList = new JSONArray();
        chatList.stream().forEach(chat -> {
            JSONObject question = new JSONObject();
            question.put("role", "user");
            question.put("content", chat.getQuestion());
            questionList.add(question);
            if (!StringUtils.isEmpty(chat.getResponse())) {
                JSONObject response = new JSONObject();
                response.put("role", "assistant");
                response.put("content", chat.getResponse());
                questionList.add(response);
            }
        });
        param.put("messages", questionList);
        param.put("stream", true);
        return param.toString();
    }

    public SseEmitter generate_test(OllamaChatDto ollamaChatDto) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        executor.execute(() -> {
            for (int index = 0; index < 600; index++) {
                try {
                    emitter.send("test" + index + "\\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            emitter.complete();
        });
        return emitter;
    }
}
