package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dto.ComfyUiDto;
import com.heeexy.example.service.ComfyUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/comfy-ui")
public class ComfyUIController {
    /**
     * ComfyUI服务
     */
    @Autowired
    private ComfyUIService comfyUIService;

    @PostMapping("/generate-image")
    public String generateImage(@RequestBody ComfyUiDto comfyUiDto, HttpServletResponse response) {
        return comfyUIService.generateImage(comfyUiDto, response);
    }
}
