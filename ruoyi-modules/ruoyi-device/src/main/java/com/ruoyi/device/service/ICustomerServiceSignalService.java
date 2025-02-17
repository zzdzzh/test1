package com.ruoyi.device.service;

import com.ruoyi.device.domain.CustomerServiceRequest;

public interface ICustomerServiceSignalService {
    /**
     * Send manual customer service signal
     * @param request Customer service request containing device and user information
     * @return true if signal sent successfully, false otherwise
     */
    boolean sendManualServiceSignal(CustomerServiceRequest request);
}
