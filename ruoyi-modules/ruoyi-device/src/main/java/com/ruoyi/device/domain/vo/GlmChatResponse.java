package com.ruoyi.device.domain.vo;

import java.util.List;
import lombok.Data;

/**
 * GLM聊天响应结果
 */
@Data
public class GlmChatResponse {
    
    /**
     * 任务ID
     */
    private String id;
    
    /**
     * 请求ID
     */
    private String requestId;
    
    /**
     * 模型名称
     */
    private String model;
    
    /**
     * 创建时间
     */
    private Long created;
    
    /**
     * 任务状态
     */
    private String taskStatus;
    
    /**
     * 选择结果
     */
    private List<Choice> choices;
    
    /**
     * Token使用统计
     */
    private Usage usage;
    
    @Data
    public static class Choice {
        private Integer index;
        private String finishReason;
        private Message message;
    }
    
    @Data
    public static class Message {
        private String role;
        private String content;
        private List<GlmChatRequest.ToolCall> toolCalls;
    }
    
    @Data
    public static class Usage {
        private Integer promptTokens;
        private Integer completionTokens;
        private Integer totalTokens;
    }
} 