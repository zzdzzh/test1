package com.ruoyi.device.service.impl;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.device.domain.vo.GlmChatRequest;
import com.ruoyi.device.domain.vo.GlmChatResponse;
import com.ruoyi.device.service.IGlmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson2.JSONObject;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;

/**
 * GLM-4 API服务实现类
 */
@Slf4j
@Service
public class GlmServiceImpl implements IGlmService {

    @Value("${glm.api.key}")
    private String apiKey;

    @Value("${glm.api.base-url:https://open.bigmodel.cn/api/paas/v4}")
    private String baseUrl;

    private RestTemplate createRestTemplate() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(15000);
            factory.setReadTimeout(30000);

            return new RestTemplate(factory);
        } catch (Exception e) {
            throw new RuntimeException("创建RestTemplate失败", e);
        }
    }

    @Override
    public GlmChatResponse chat(GlmChatRequest request) {
        String url = baseUrl + "/chat/completions";
        return doRequest(url, request);
    }

    @Override
    public String asyncChat(GlmChatRequest request) {
        String url = baseUrl + "/async/chat/completions";
        GlmChatResponse response = doRequest(url, request);
        return response.getId();
    }

    @Override
    public GlmChatResponse getAsyncResult(String taskId) {
        if (StringUtils.isEmpty(taskId)) {
            throw new IllegalArgumentException("taskId不能为空");
        }
        String url = baseUrl + "/async-result/" + taskId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        
        try {
            RestTemplate restTemplate = createRestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, entity);
            log.info("GLM API响应: {}", response.getBody());
            return JSONObject.parseObject(response.getBody(), GlmChatResponse.class);
        } catch (Exception e) {
            log.error("调用GLM API失败: {}", e.getMessage(), e);
            throw new RuntimeException("调用GLM API失败: " + e.getMessage());
        }
    }

    private GlmChatResponse doRequest(String url, GlmChatRequest request) {
        // String apiKey = "b72aa9e36abc4ba49f0719e4e01610d5.V6Y0BtH7cKe99Ty5";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        try {
            log.info("请求GLM API, URL: {}, 请求参数: {}", url, JSONObject.toJSONString(request));
            log.info("apiKey: {}", apiKey);
            RestTemplate restTemplate = createRestTemplate();
            HttpEntity<GlmChatRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            log.info("GLM API响应: {}", response.getBody());
            return JSONObject.parseObject(response.getBody(), GlmChatResponse.class);
        } catch (Exception e) {
            log.error("调用GLM API失败: {}", e.getMessage(), e);
            throw new RuntimeException("调用GLM API失败: " + e.getMessage());
        }
    }
} 