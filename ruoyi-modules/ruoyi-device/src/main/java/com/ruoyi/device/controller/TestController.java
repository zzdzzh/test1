package com.ruoyi.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.device.service.ITestService;
import com.ruoyi.device.domain.TestData;

/**
 * 测试Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController
{
    @Autowired
    private ITestService testService;

    /**
     * 获取测试消息
     */
    //@RequiresPermissions("device:test:list")
    @GetMapping("/message/{name}")
    public AjaxResult getMessage(@PathVariable("name") String name)
    {
        String message = testService.getMessage(name);
        return success(message);
    }
    
    /**
     * 获取测试数据
     */
    @RequiresPermissions("device:test:query")
    @GetMapping("/data")
    public AjaxResult getData()
    {
        return success(new TestData("001", "测试数据"));
    }

    /**
     * 新增测试数据
     */
    @RequiresPermissions("device:test:add")
    @Log(title = "测试管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TestData data)
    {
        // 这里添加业务逻辑
        return success("新增成功");
    }

    /**
     * 修改测试数据
     */
    @RequiresPermissions("device:test:edit")
    @Log(title = "测试管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TestData data)
    {
        // 这里添加业务逻辑
        return success("修改成功");
    }

    /**
     * 删除测试数据
     */
    @RequiresPermissions("device:test:remove")
    @Log(title = "测试管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id)
    {
        // 这里添加业务逻辑
        return success("删除成功");
    }
} 