package com.heeexy.example.dto;

import lombok.Data;

/**
 * ollama单问答实例
 */
@Data
public class OllamaChatInfoEntity {

    /**
     * 问题
     */
    private String question;

    /**
     * 回答
     */
    private String response;
}
