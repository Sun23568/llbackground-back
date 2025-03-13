package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dto.ComfyUiDto;
import com.heeexy.example.util.ComfyUiTool;
import com.heeexy.example.util.OkHttpClientUtils;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * ComfyUI服务
 */
@Component
public class ComfyUIService {
    /**
     * ComfyUI地址
     */
    @Value("${comfy-ui.url}")
    private String comfyUrl;

    /**
     * ComfyUI预览地址
     */
    @Value("${comfy-ui.view-url}")
    private String comfyViewUrl;

    /**
     * http服务
     */
    private final OkHttpClient client = new OkHttpClient();

    /**
     * 生成图片
     *
     * @param comfyUiDto
     * @param response
     * @return
     */
    public String generateImage(ComfyUiDto comfyUiDto, HttpServletResponse response) {
        // 设置返回为图片
        response.setHeader("Content-Type", "image/png");

        // 获取请求Prompt参数
        JSONObject jsonParam = ComfyUiTool.getComfyUiParamByKey(comfyUiDto.getKeyWord());

        // 请求prompt
        String promptResp = OkHttpClientUtils.post(comfyUrl + "prompt", jsonParam.toString());
        // 获取prompt_id
        String promptId = JSONObject.parseObject(promptResp).getString("prompt_id");
        // 请求history
        JSONObject historyResp = JSONObject.parseObject("{}");
        while (historyResp.isEmpty()) {
            String historyRespStr = OkHttpClientUtils.get(comfyUrl + "history/" + promptId);
            historyResp = JSONObject.parseObject(historyRespStr);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 获取文件名与类型
        String fileName = historyResp.getJSONObject(promptId).getJSONObject("outputs").getJSONObject("18")
                .getJSONArray("images").getJSONObject(0).getString("filename");
        // response文件名
        response.setHeader("Content-Disposition", "filename=" + fileName);
        String type = historyResp.getJSONObject(promptId).getJSONObject("outputs").getJSONObject("18")
                .getJSONArray("images").getJSONObject(0).getString("type");
        // 请求view
//        String viewResp = OkHttpClientUtils.get(comfyUrl + "view?filename=" + fileName + "&type=" + type);
        // 返回base64
        return comfyViewUrl + "view?filename=" + fileName + "&type=" + type;
    }
}