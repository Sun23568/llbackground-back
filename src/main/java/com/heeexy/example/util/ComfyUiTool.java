package com.heeexy.example.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

/**
 * ComfyUI工具类
 */
@Component
public class ComfyUiTool {

    /**
     * ComfyUI的JSON文件路径
     */
    @Value("${comfy-ui.json-path}")
    private String jsonPath;

    // 静态方法获取实例
    private static ComfyUiTool instance;

    // 构造方法
    public ComfyUiTool() {
        instance = this;
    }

    public static JSONObject getComfyUiParamByKey(String keyWord) {
        // 使用实例获取jsonPath
        String fileContent = readFile(instance.jsonPath);
        JSONObject jsonObject = JSONObject.parseObject(fileContent);
        // 随机种子
        JSONObject seedInputs = jsonObject.getJSONObject("prompt").getJSONObject("3").getJSONObject("inputs");
        seedInputs.put("seed", new BigDecimal(new Random().nextInt(1000000000)));
        // 关键词赋值
        JSONObject keyWordInputs = jsonObject.getJSONObject("prompt").getJSONObject("6").getJSONObject("inputs");
        keyWordInputs.put("text", keyWord);
        // 返回
        return jsonObject;
    }

    // 读取文件内容的方法
    private static String readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}