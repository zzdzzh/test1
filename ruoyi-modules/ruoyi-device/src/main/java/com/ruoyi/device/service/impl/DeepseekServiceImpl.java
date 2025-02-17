package com.ruoyi.device.service.impl;

import com.ruoyi.device.service.IDeepseekService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Deepseek AI服务实现类
 */
@Slf4j
@Service
public class DeepseekServiceImpl implements IDeepseekService {

    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${deepseek.api.key:sk-7015edb6ee4a42e3a711e987cac8bc2a}")
    private String apiKey;

    private static final String API_URL = "https://api.deepseek.com/chat/completions";

    public DeepseekServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.webClient = WebClient.builder()
                .baseUrl(API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Map<String, Object> chatCompletion(List<Map<String, String>> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat");  // "deepseek-reasoner");  // "deepseek-chat");
        requestBody.put("messages", messages);
        requestBody.put("stream", false);

        try {
            log.info("请求Deepseek API, URL: {}, 请求参数: {}", API_URL, requestBody);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);
            log.info("Deepseek API响应: {}", response.getBody());
            return response.getBody();
        } catch (Exception e) {
            log.error("调用Deepseek API失败: {}", e.getMessage(), e);
            throw new RuntimeException("调用Deepseek API失败: " + e.getMessage());
        }
    }

    /**
     * 流式调用Deepseek聊天接口，收集完整响应后返回
     *
     * @param messages 消息列表
     * @return 完整的响应内容
     */
    @Override
    public String chatCompletionStream(List<Map<String, String>> messages) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat");
        requestBody.put("messages", messages);
        requestBody.put("stream", true);

        log.info("开始流式请求Deepseek API, URL: {}, 请求参数: {}", API_URL, requestBody);

        return webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(String.class)
                .map(chunk -> {
                    try {
                        if (chunk.startsWith("data: ")) {
                            chunk = chunk.substring(6);
                        }
                        if ("[DONE]".equals(chunk)) {
                            return "";
                        }
                        Map<String, Object> response = objectMapper.readValue(chunk, Map.class);
                        Map<String, Object> choices = ((List<Map<String, Object>>) response.get("choices")).get(0);
                        Map<String, Object> delta = (Map<String, Object>) choices.get("delta");
                        String content = (String) delta.get("content");
                        log.info("流式响应内容: {}", content);
                        return content != null ? content : "";
                    } catch (Exception e) {
                        log.error("处理流式响应失败: {}", e.getMessage(), e);
                        return "";
                    }
                })
                .reduce(new StringBuilder(), (sb, content) -> sb.append(content))
                .map(StringBuilder::toString)
                .doOnError(error -> log.error("流式调用Deepseek API失败: {}", error.getMessage(), error))
                .onErrorResume(error -> {
                    log.error("处理流式响应错误: {}", error.getMessage());
                    return Mono.just("调用AI服务出错: " + error.getMessage());
                })
                .block();  // 阻塞等待所有响应收集完成
    }
}