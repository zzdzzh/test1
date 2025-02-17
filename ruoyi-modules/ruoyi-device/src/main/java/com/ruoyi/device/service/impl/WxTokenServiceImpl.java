package com.ruoyi.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.device.service.IWxTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson2.JSONObject;
import java.util.concurrent.TimeUnit;

/**
 * 微信Token服务实现
 * 
 * @author ruoyi
 */
@Service
public class WxTokenServiceImpl implements IWxTokenService {
    
    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Autowired
    private RedisService redisService;

    private static final String WX_TOKEN_KEY = "wx:access_token";
    private static final String WX_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    private static final String WX_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    @Override
    public String getAccessToken() {
        String token = redisService.getCacheObject(WX_TOKEN_KEY);
        if (StringUtils.isNotEmpty(token)) {
            return token;
        }
        return refreshAccessToken();
    }

    @Override
    public String refreshAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(WX_TOKEN_URL, appId, appSecret);
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonObject = JSONObject.parseObject(response);
        
        if (jsonObject.containsKey("access_token")) {
            String accessToken = jsonObject.getString("access_token");
            // 微信access_token有效期为7200秒，这里设置缓存时间为7000秒
            redisService.setCacheObject(WX_TOKEN_KEY, accessToken, 7000L, TimeUnit.SECONDS);
            return accessToken;
        }
        
        throw new RuntimeException("获取微信access_token失败：" + jsonObject.getString("errmsg"));
    }

    @Override
    public String getOpenid(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(WX_OPENID_URL, appId, appSecret, code);
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonObject = JSONObject.parseObject(response);
        
        if (jsonObject.containsKey("openid")) {
            return jsonObject.getString("openid");
        }
        
        throw new RuntimeException("获取微信openid失败：" + jsonObject.getString("errmsg"));
    }
} 