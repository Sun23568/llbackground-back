package com.llback.core.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AI服务地址配置属性类
 * <p>
 * 根据active-profile自动选择local或remote环境的配置
 *
 * @author HaleyAI
 * @date 2026/1/10
 */
@Data
@Component
@ConfigurationProperties(prefix = "ai")
public class AiUrlProperties {

    /**
     * 当前激活的环境：local(本地) 或 remote(远程)
     */
    private String activeProfile;

    /**
     * 本地环境配置
     */
    private ProfileConfig local;

    /**
     * 远程环境配置
     */
    private ProfileConfig remote;

    /**
     * 获取当前激活环境的Ollama服务地址
     *
     * @return Ollama服务地址
     */
    public String getActiveOllamaUrl() {
        return getActiveProfile().getOllamaUrl();
    }

    /**
     * 获取当前激活环境的ComfyUI服务地址
     *
     * @return ComfyUI服务地址
     */
    public String getActiveComfyUiUrl() {
        return getActiveProfile().getComfyUiUrl();
    }

    /**
     * 获取当前激活的环境配置
     *
     * @return 环境配置对象
     */
    private ProfileConfig getActiveProfile() {
        if ("remote".equalsIgnoreCase(activeProfile)) {
            return remote;
        }
        return local; // 默认使用本地
    }

    /**
     * 环境配置（本地/远程）
     */
    @Data
    public static class ProfileConfig {
        /**
         * Ollama服务地址
         */
        private String ollamaUrl;

        /**
         * ComfyUI服务地址
         */
        private String comfyUiUrl;
    }
}
