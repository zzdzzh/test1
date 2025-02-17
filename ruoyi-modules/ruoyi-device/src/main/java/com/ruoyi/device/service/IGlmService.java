package com.ruoyi.device.service;

import com.ruoyi.device.domain.vo.GlmChatRequest;
import com.ruoyi.device.domain.vo.GlmChatResponse;

/**
 * GLM-4 API服务接口
 */
public interface IGlmService {
    
    /**
     * 同步调用GLM-4聊天接口
     * @param request 聊天请求参数
     * @return 聊天响应结果
     */
    GlmChatResponse chat(GlmChatRequest request);
    
    /**
     * 异步调用GLM-4聊天接口
     * @param request 聊天请求参数
     * @return 任务ID
     */
    String asyncChat(GlmChatRequest request);
    
    /**
     * 查询异步调用结果
     * @param taskId 任务ID
     * @return 聊天响应结果
     */
    GlmChatResponse getAsyncResult(String taskId);
} 