package com.ruoyi.device.service;

/**
 * 微信Token服务接口
 * 
 * @author ruoyi
 */
public interface IWxTokenService {
    /**
     * 获取微信access_token
     * 
     * @return access_token
     */
    String getAccessToken();

    /**
     * 刷新微信access_token
     * 
     * @return access_token
     */
    String refreshAccessToken();

    /**
     * 获取微信openid
     * 
     * @param code 微信授权code
     * @return openid
     */
    String getOpenid(String code);
} 