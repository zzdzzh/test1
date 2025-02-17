package com.ruoyi.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.device.domain.CustomerServiceRequest;
import com.ruoyi.device.service.ICustomerServiceSignalService;

@RestController
@RequestMapping("/customer-service")
public class CustomerServiceController {
    
    @Autowired
    private ICustomerServiceSignalService customerServiceSignalService;

    /**
     * Send manual customer service signal
     */
    @PostMapping("/manual-signal")
    public AjaxResult sendManualServiceSignal(@RequestBody CustomerServiceRequest request) {
        boolean result = customerServiceSignalService.sendManualServiceSignal(request);
        return result ? AjaxResult.success() : AjaxResult.error("Failed to send manual service signal");
    }
}
