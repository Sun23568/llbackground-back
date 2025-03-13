package com.heeexy.example.controller;

import com.heeexy.example.dto.OllamaChatDto;
import com.heeexy.example.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/ollama")
public class OllamaController {

    @Autowired
    private OllamaService ollamaService;

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/event-stream; charset=utf-8")
    public SseEmitter generate(@RequestBody OllamaChatDto ollamaChatDto, HttpServletResponse response) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        ollamaService.generate(ollamaChatDto, response);
        return emitter;
    }

    @PostMapping(value = "/chat1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/event-stream; charset=utf-8")
    public SseEmitter generate_test(@RequestBody OllamaChatDto ollamaChatDto, HttpServletResponse response) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        ollamaService.generateTest(ollamaChatDto, response);
        return emitter;
    }
}
