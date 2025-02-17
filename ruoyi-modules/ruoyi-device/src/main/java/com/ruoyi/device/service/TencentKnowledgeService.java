package com.ruoyi.device.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TencentKnowledgeService {
    
    @Value("${tencent.knowledge.api-key:sk-YXa5jeRBshY4dKe1Giijql4ZBxV20MCHOCcC1pd9dQI3mC6y}")
    private String apiKey;
    
    @Value("${tencent.knowledge.base-url:https://api.lkeap.cloud.tencent.com/v1}")
    private String baseUrl;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public TencentKnowledgeService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
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
}
