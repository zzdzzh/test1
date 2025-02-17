package com.ruoyi.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.device.domain.WxMenu;
import com.ruoyi.device.service.IWxMenuService;

/**
 * 微信公众号菜单控制器
 */
@RestController
@RequestMapping("/wx/menu")
public class WxMenuController extends BaseController {
    @Autowired
    private IWxMenuService wxMenuService;

    /**
     * 创建菜单
     */
    // @RequiresPermissions("device:wx:menu:create")
    @Log(title = "微信菜单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult create(@RequestBody WxMenu menu) {
        String result = wxMenuService.createMenu(menu);
        return AjaxResult.success(result);
    }

    /**
     * 查询菜单
     */
    // @RequiresPermissions("device:wx:menu:query")
    @GetMapping
    public AjaxResult getMenu() {
        WxMenu menu = wxMenuService.getMenu();
        return AjaxResult.success(menu);
    }

    /**
     * 删除菜单
     */
    // @RequiresPermissions("device:wx:menu:delete")
    @Log(title = "微信菜单", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult delete() {
        String result = wxMenuService.deleteMenu();
        return AjaxResult.success(result);
    }
} 