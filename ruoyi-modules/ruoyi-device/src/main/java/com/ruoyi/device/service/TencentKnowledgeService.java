package com.ruoyi.device.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TencentKnowledgeService {
    
    @Value("${tencent.knowledge.api-key:sk-o8T4RMN9fj50FO4w6Oa8qWv6Bc7la9qvOl3wwJzwt9yvF0bW}")
    private String apiKey;
    
    @Value("${tencent.knowledge.base-url:https://api.lkeap.cloud.tencent.com/v1}")
    private String baseUrl;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;
    
    public TencentKnowledgeService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.webClient = WebClient.builder().build();
    }
    
    public String analyzeText(String content) {
        String url = baseUrl + "/chat/completions";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", content);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-r1");
        requestBody.put("messages", new Object[]{message});
        requestBody.put("stream", false);
        
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        
        try {
            String response = restTemplate.postForObject(url, request, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to analyze text: " + e.getMessage(), e);
        }
    }
    
    public CompletableFuture<String> analyzeTextStream(String content) {
        String url = baseUrl + "/chat/completions";
        
        // content输出日志
        log.info("content: {}", content);
        
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", content);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-r1");
        requestBody.put("messages", new Object[]{message});
        requestBody.put("stream", true);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 4096);
        
        CompletableFuture<String> future = new CompletableFuture<>();
        StringBuilder responseBuilder = new StringBuilder();

        webClient.post()
            .uri(url)
            .header("Authorization", "Bearer " + apiKey)
            .header("Accept", "text/event-stream")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.TEXT_EVENT_STREAM)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToFlux(String.class)
            .publishOn(reactor.core.scheduler.Schedulers.boundedElastic())
            .map(chunk -> {
                try {
                    if (chunk.startsWith("data: ")) {
                        chunk = chunk.substring(6);
                    }
                    if (chunk.equals("[DONE]")) {
                        return "";
                    }
                    JsonNode jsonNode = objectMapper.readTree(chunk);
                    String textContent = jsonNode.path("choices")
                                          .path(0)
                                          .path("delta")
                                          .path("content")
                                          .asText("");
                    return textContent;
                } catch (Exception e) {
                    return "";
                }
            })
            .doOnNext(text -> {
                synchronized (responseBuilder) {
                    if (!text.isEmpty()) {
                        responseBuilder.append(text);
                    }
                }
            })
            .doOnComplete(() -> {
                String result;
                synchronized (responseBuilder) {
                    result = responseBuilder.toString();
                }
                future.complete(result);
            })
            .doOnError(e -> {
                if (e instanceof WebClientResponseException) {
                    WebClientResponseException wce = (WebClientResponseException) e;
                    future.completeExceptionally(new RuntimeException("Failed to analyze text: " + wce.getResponseBodyAsString()));
                } else {
                    future.completeExceptionally(new RuntimeException("Failed to analyze text: " + e.getMessage()));
                }
            })
            .doOnCancel(() -> {
                future.completeExceptionally(new RuntimeException("Request was cancelled"));
            })
            .onErrorResume(e -> Flux.empty())
            .subscribe();

        reactor.core.scheduler.Schedulers.boundedElastic().schedule(() -> {
            if (!future.isDone()) {
                future.completeExceptionally(new RuntimeException("Request timed out after 180 seconds"));
            }
        }, 180, java.util.concurrent.TimeUnit.SECONDS);

        return future;
    }
}
