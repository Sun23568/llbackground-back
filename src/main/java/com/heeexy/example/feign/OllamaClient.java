package com.heeexy.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.InputStream;

@FeignClient(name = "ollama", url = "${ollama.url}")
public interface OllamaClient {
    /**
     * 初始化
     *
     * @param param
     * @return
     */
    @PostMapping("/api/generate")
    ResponseEntity<InputStream> generate(@RequestBody String param);
}
