package com.ruoyi.device.service;

import com.ruoyi.device.domain.WxMenu;

/**
 * 微信公众号菜单服务接口
 */
public interface IWxMenuService {
    /**
     * 创建微信公众号菜单
     * 
     * @param menu 菜单配置
     * @return 创建结果
     */
    public String createMenu(WxMenu menu);

    /**
     * 查询微信公众号菜单
     * 
     * @return 菜单配置
     */
    public WxMenu getMenu();

    /**
     * 删除微信公众号菜单
     * 
     * @return 删除结果
     */
    public String deleteMenu();
} 