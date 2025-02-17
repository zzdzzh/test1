package com.ruoyi.device.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

@Service
public class OllamaService {
    private static final Logger log = LoggerFactory.getLogger(OllamaService.class);
    
    @Value("${ollama.api.url:http://localhost:11434/api/generate}")
    private String ollamaApiUrl;
    
    @Value("${ollama.model.name:qwen2}")
    private String modelName;
    
    private final RestTemplate restTemplate;
    
    public OllamaService() {
        this.restTemplate = new RestTemplate();
    }
    
    public String generateResponse(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", modelName);
            requestBody.put("prompt", prompt);
            requestBody.put("stream", false);
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            log.info("Sending request to Ollama API: {}", prompt);
            Map<String, Object> response = restTemplate.postForObject(ollamaApiUrl, request, Map.class);
            
            if (response != null && response.containsKey("response")) {
                String aiResponse = (String) response.get("response");
                log.info("Received response from Ollama: {}", aiResponse);
                return aiResponse;
            }
            
            return "AI模型暂时无法回复，请稍后再试。";
            
        } catch (Exception e) {
            log.error("Error calling Ollama API", e);
            return "抱歉，AI服务暂时不可用，请稍后再试。";
        }
    }
} 