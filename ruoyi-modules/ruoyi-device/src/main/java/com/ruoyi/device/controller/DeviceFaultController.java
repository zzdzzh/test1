package com.ruoyi.device.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.device.domain.DeviceFault;
import com.ruoyi.device.service.IDeviceFaultService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 设备故障Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/device/fault")
public class DeviceFaultController extends BaseController {
    @Autowired
    private IDeviceFaultService deviceFaultService;

    /**
     * 查询故障列表
     */
    //@RequiresPermissions("device:fault:list")
    @GetMapping("/list")
    public TableDataInfo list(DeviceFault deviceFault) {
        startPage();
        List<DeviceFault> list = deviceFaultService.selectDeviceFaultList(deviceFault);
        return getDataTable(list);
    }

    /**
     * 新增故障记录
     */
    @RequiresPermissions("device:fault:add")
    @Log(title = "设备故障", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceFault deviceFault) {
        return toAjax(deviceFaultService.insertDeviceFault(deviceFault));
    }

    /**
     * 删除故障记录
     */
    @RequiresPermissions("device:fault:remove")
    @Log(title = "设备故障", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(deviceFaultService.deleteDeviceFaultByIds(ids));
    }
} 