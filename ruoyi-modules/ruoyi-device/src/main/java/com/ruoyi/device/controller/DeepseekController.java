package com.ruoyi.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.device.service.IDeepseekService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

/**
 * Deepseek AI服务控制器
 * 
 * @author ruoyi
 */
@Tag(name = "Deepseek AI服务")
@RestController
@RequestMapping("/deepseek")
public class DeepseekController extends BaseController {
    @Autowired
    private IDeepseekService deepseekService;

    /**
     * 生成AI回复
     */
    @Operation(summary = "生成AI回复")
    @PostMapping("/chat")
    public AjaxResult chat(@RequestBody List<Map<String, String>> messages) {
        Map<String, Object> response = deepseekService.chatCompletion(messages);
        return AjaxResult.success("生成成功", response);
    }

    /**
     * 流式生成AI回复
     */
    @Operation(summary = "流式生成AI回复")
    @PostMapping("/chat/stream")
    public AjaxResult chatStream(@RequestBody List<Map<String, String>> messages) {
        String response = deepseekService.chatCompletionStream(messages);
        return AjaxResult.success("生成成功", response);
    }
}