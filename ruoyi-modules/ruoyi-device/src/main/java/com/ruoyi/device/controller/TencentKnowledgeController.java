package com.ruoyi.device.controller;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.device.service.TencentKnowledgeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/knowledge")
public class TencentKnowledgeController {
    
    private final TencentKnowledgeService knowledgeService;
    
    public TencentKnowledgeController(TencentKnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }
    
    @PostMapping("/analyze")
    public AjaxResult analyzeText(@RequestBody Map<String, String> request) {
        String content = request.get("content");
        if (content == null || content.trim().isEmpty()) {
            return AjaxResult.error("Content cannot be empty");
        }
        
        try {
            String result = knowledgeService.analyzeText(content);
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
