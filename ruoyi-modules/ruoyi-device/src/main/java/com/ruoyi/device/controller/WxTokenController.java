package com.ruoyi.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.device.service.IWxTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 微信Token管理 控制器
 * 
 * @author ruoyi
 */
@Tag(name = "微信Token管理")
@RestController
@RequestMapping("/wx/token")
public class WxTokenController extends BaseController {
    @Autowired
    private IWxTokenService wxTokenService;

    /**
     * 获取微信access_token
     */
    @Operation(summary = "获取微信access_token")
    @GetMapping("/access_token")
    public AjaxResult getAccessToken() {
        String token = wxTokenService.getAccessToken();
        return AjaxResult.success("获取成功", token);
    }

    /**
     * 刷新微信access_token
     */
    @Operation(summary = "刷新微信access_token")
    @GetMapping("/refresh")
    public AjaxResult refreshAccessToken() {
        String token = wxTokenService.refreshAccessToken();
        return AjaxResult.success("刷新成功", token);
    }

    /**
     * 获取微信openid
     */
    @Operation(summary = "获取微信openid")
    @GetMapping("/openid")
    public AjaxResult getOpenid(@RequestParam("code") String code) {
        String openid = wxTokenService.getOpenid(code);
        return AjaxResult.success("获取成功", openid);
    }
} 