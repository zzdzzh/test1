package com.ruoyi.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.device.service.OllamaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Ollama AI服务控制器
 * 
 * @author ruoyi
 */
@Tag(name = "Ollama AI服务")
@RestController
@RequestMapping("/ollama")
public class OllamaController extends BaseController {
    @Autowired
    private OllamaService ollamaService;

    /**
     * 生成AI回复
     */
    @Operation(summary = "生成AI回复")
    @PostMapping("/generate")
    public AjaxResult generate(@RequestBody GenerateRequest request) {
        String response = ollamaService.generateResponse(request.getPrompt());
        return AjaxResult.success("生成成功", response);
    }

    public static class GenerateRequest {
        private String prompt;

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }
    }
} 