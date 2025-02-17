package com.ruoyi.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.device.domain.WxMenu;
import com.ruoyi.device.service.IWxMenuService;
import com.ruoyi.device.service.IWxTokenService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信公众号菜单服务实现类
 */
@Service
public class WxMenuServiceImpl implements IWxMenuService {

    private static final Logger log = LoggerFactory.getLogger(WxMenuServiceImpl.class);

    private static final String WX_MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    private static final String WX_MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";
    private static final String WX_MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";

    @Autowired
    private IWxTokenService wxTokenService;

    @Override
    public String createMenu(WxMenu menu) {
        String accessToken = wxTokenService.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            return "获取access_token失败";
        }
        // 打印access_token
        log.info("获取到的access_token: {}", accessToken);
        
        String url = WX_MENU_CREATE_URL + accessToken;
        
        // 打印完整的menu对象
        log.info("创建菜单请求参数: {}", JSON.toJSONString(menu));
        
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(url, menu, String.class);
        
        // 打印返回结果
        log.info("创建菜单返回结果: {}", result);
        
        return result;
    }

    @Override
    public WxMenu getMenu() {
        String accessToken = wxTokenService.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }
        String url = WX_MENU_GET_URL + accessToken;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("查询菜单返回结果: {}", response);
        
        // 解析返回的JSON
        JSONObject jsonObject = JSON.parseObject(response);
        if (jsonObject.containsKey("menu")) {
            return JSON.parseObject(jsonObject.getString("menu"), WxMenu.class);
        }
        return null;
    }

    @Override
    public String deleteMenu() {
        String accessToken = wxTokenService.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            return "获取access_token失败";
        }
        String url = WX_MENU_DELETE_URL + accessToken;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        log.info("删除菜单返回结果: {}", result);
        return result;
    }
} 