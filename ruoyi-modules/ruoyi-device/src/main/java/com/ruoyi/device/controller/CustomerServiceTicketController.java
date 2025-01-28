package com.ruoyi.device.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.device.domain.CustomerServiceTicket;
import com.ruoyi.device.service.ICustomerServiceTicketService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 客户服务工单Controller
 */
@RestController
@RequestMapping("/customer/service/ticket")
public class CustomerServiceTicketController extends BaseController
{
    @Autowired
    private ICustomerServiceTicketService customerServiceTicketService;

    /**
     * 查询客户服务工单列表
     */
    //@RequiresPermissions("device:ticket:list")
    @GetMapping("/list")
    public TableDataInfo list(CustomerServiceTicket customerServiceTicket)
    {
        startPage();
        List<CustomerServiceTicket> list = customerServiceTicketService.selectCustomerServiceTicketList(customerServiceTicket);
        return getDataTable(list);
    }

    /**
     * 导出客户服务工单列表
     */
    @RequiresPermissions("device:ticket:export")
    @Log(title = "客户服务工单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerServiceTicket customerServiceTicket)
    {
        List<CustomerServiceTicket> list = customerServiceTicketService.selectCustomerServiceTicketList(customerServiceTicket);
        ExcelUtil<CustomerServiceTicket> util = new ExcelUtil<CustomerServiceTicket>(CustomerServiceTicket.class);
        util.exportExcel(response, list, "客户服务工单数据");
    }

    /**
     * 获取客户服务工单详细信息
     */
    @RequiresPermissions("device:ticket:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(customerServiceTicketService.selectCustomerServiceTicketById(id));
    }

    /**
     * 新增客户服务工单
     */
    @RequiresPermissions("device:ticket:add")
    @Log(title = "客户服务工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerServiceTicket customerServiceTicket)
    {
        return toAjax(customerServiceTicketService.insertCustomerServiceTicket(customerServiceTicket));
    }

    /**
     * 修改客户服务工单
     */
    @RequiresPermissions("device:ticket:edit")
    @Log(title = "客户服务工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerServiceTicket customerServiceTicket)
    {
        return toAjax(customerServiceTicketService.updateCustomerServiceTicket(customerServiceTicket));
    }

    /**
     * 删除客户服务工单
     */
    @RequiresPermissions("device:ticket:remove")
    @Log(title = "客户服务工单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(customerServiceTicketService.deleteCustomerServiceTicketByIds(ids));
    }
} 