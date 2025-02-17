package com.ruoyi.device.domain.vo;

import java.util.List;
import lombok.Data;

/**
 * GLM聊天请求参数
 */
@Data
public class GlmChatRequest {
    
    /**
     * 模型名称
     */
    private String model;
    
    /**
     * 对话消息列表
     */
    private List<Message> messages;
    
    /**
     * 是否流式输出
     */
    private Boolean stream;
    
    /**
     * 温度参数
     */
    private Float temperature;
    
    /**
     * 随机采样参数
     */
    private Float topP;
    
    /**
     * 最大输出token数
     */
    private Integer maxTokens;
    
    /**
     * 工具列表
     */
    private List<Tool> tools;
    
    /**
     * 工具选择
     */
    private String toolChoice;
    
    /**
     * 用户ID
     */
    private String userId;
    
    @Data
    public static class Message {
        private String role;
        private String content;
        private List<ToolCall> toolCalls;
        private String toolCallId;
    }
    
    @Data
    public static class Tool {
        private String type;
        private Function function;
    }
    
    @Data
    public static class Function {
        private String name;
        private String description;
        private Object parameters;
    }
    
    @Data
    public static class ToolCall {
        private String id;
        private String type;
        private Function function;
    }
} 