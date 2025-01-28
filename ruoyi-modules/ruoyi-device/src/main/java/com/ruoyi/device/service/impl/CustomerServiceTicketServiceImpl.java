package com.ruoyi.device.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.device.mapper.CustomerServiceTicketMapper;
import com.ruoyi.device.domain.CustomerServiceTicket;
import com.ruoyi.device.service.ICustomerServiceTicketService;

/**
 * 客户服务工单Service业务层处理
 */
@Service
public class CustomerServiceTicketServiceImpl implements ICustomerServiceTicketService 
{
    @Autowired
    private CustomerServiceTicketMapper customerServiceTicketMapper;

    /**
     * 查询客户服务工单
     * 
     * @param id 客户服务工单主键
     * @return 客户服务工单
     */
    @Override
    public CustomerServiceTicket selectCustomerServiceTicketById(Long id)
    {
        return customerServiceTicketMapper.selectCustomerServiceTicketById(id);
    }

    /**
     * 查询客户服务工单列表
     * 
     * @param customerServiceTicket 客户服务工单
     * @return 客户服务工单
     */
    @Override
    public List<CustomerServiceTicket> selectCustomerServiceTicketList(CustomerServiceTicket customerServiceTicket)
    {
        return customerServiceTicketMapper.selectCustomerServiceTicketList(customerServiceTicket);
    }

    /**
     * 新增客户服务工单
     * 
     * @param customerServiceTicket 客户服务工单
     * @return 结果
     */
    @Override
    public int insertCustomerServiceTicket(CustomerServiceTicket customerServiceTicket)
    {
        return customerServiceTicketMapper.insertCustomerServiceTicket(customerServiceTicket);
    }

    /**
     * 修改客户服务工单
     * 
     * @param customerServiceTicket 客户服务工单
     * @return 结果
     */
    @Override
    public int updateCustomerServiceTicket(CustomerServiceTicket customerServiceTicket)
    {
        return customerServiceTicketMapper.updateCustomerServiceTicket(customerServiceTicket);
    }

    /**
     * 批量删除客户服务工单
     * 
     * @param ids 需要删除的客户服务工单主键
     * @return 结果
     */
    @Override
    public int deleteCustomerServiceTicketByIds(Long[] ids)
    {
        return customerServiceTicketMapper.deleteCustomerServiceTicketByIds(ids);
    }

    /**
     * 删除客户服务工单信息
     * 
     * @param id 客户服务工单主键
     * @return 结果
     */
    @Override
    public int deleteCustomerServiceTicketById(Long id)
    {
        return customerServiceTicketMapper.deleteCustomerServiceTicketById(id);
    }
} 