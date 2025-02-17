package com.ruoyi.device.service;

import java.util.List;
import java.util.Map;

/**
 * Deepseek AI服务接口
 */
public interface IDeepseekService {
    /**
     * 调用Deepseek聊天接口
     *
     * @param messages 消息列表
     * @return 响应结果
     */
    Map<String, Object> chatCompletion(List<Map<String, String>> messages);

    /**
     * 流式调用Deepseek聊天接口，收集完整响应后返回
     *
     * @param messages 消息列表
     * @return 完整的响应内容
     */
    String chatCompletionStream(List<Map<String, String>> messages);
}