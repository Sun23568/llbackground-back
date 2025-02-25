package com.heeexy.example.dto;

import lombok.Data;

import java.util.List;

/**
 * Ollama chat用
 */
@Data
public class OllamaChatDto {
    /**
     * 提问列表
     */
    private List<OllamaChatInfoEntity> chatList;

    /**
     * 模型名称
     */
    private String modelName;
}
