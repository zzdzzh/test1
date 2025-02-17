package com.ruoyi.device.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.device.domain.vo.GlmChatRequest;
import com.ruoyi.device.domain.vo.GlmChatResponse;
import com.ruoyi.device.service.IGlmService;
import org.springframework.web.bind.annotation.*;

/**
 * GLM-4 API接口
 */
@RestController
@RequestMapping("/glm")
public class GlmController extends BaseController {

    private final IGlmService glmService;

    public GlmController(IGlmService glmService) {
        this.glmService = glmService;
    }

    /**
     * 同步调用GLM-4聊天接口
     */
    @PostMapping("/chat")
    public AjaxResult chat(@RequestBody GlmChatRequest request) {
        GlmChatResponse response = glmService.chat(request);
        return AjaxResult.success(response);
    }

    /**
     * 异步调用GLM-4聊天接口
     */
    @PostMapping("/async/chat")
    public AjaxResult asyncChat(@RequestBody GlmChatRequest request) {
        String taskId = glmService.asyncChat(request);
        return AjaxResult.success("操作成功", taskId);
    }

    /**
     * 查询异步调用结果
     */
    @GetMapping("/async/result/{taskId}")
    public AjaxResult getAsyncResult(@PathVariable String taskId) {
        GlmChatResponse response = glmService.getAsyncResult(taskId);
        return AjaxResult.success(response);
    }
} 