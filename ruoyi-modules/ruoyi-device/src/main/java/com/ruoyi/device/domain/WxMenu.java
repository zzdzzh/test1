package com.ruoyi.device.domain;

import java.util.List;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信公众号菜单对象
 */
public class WxMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 菜单按钮列表 */
    private List<WxMenu> button;

    /** 菜单类型 */
    private String type;

    /** 菜单名称 */
    private String name;

    /** 菜单key */
    private String key;

    /** 网页链接 */
    private String url;

    /** 小程序appid */
    @JsonProperty("appid")
    private String appId;

    /** 小程序页面路径 */
    @JsonProperty("pagepath")
    private String pagePath;

    /** 子菜单列表 */
    @JsonProperty("sub_button")
    private List<WxMenu> subButton;

    public List<WxMenu> getButton() {
        return button;
    }

    public void setButton(List<WxMenu> button) {
        this.button = button;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public List<WxMenu> getSubButton() {
        return subButton;
    }

    public void setSubButton(List<WxMenu> subButton) {
        this.subButton = subButton;
    }
} 