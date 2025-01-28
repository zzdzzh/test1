package com.ruoyi.device.mapper;

import java.util.List;
import com.ruoyi.device.domain.CustomerServiceTicket;

/**
 * 客户服务工单Mapper接口
 */
public interface CustomerServiceTicketMapper 
{
    /**
     * 查询客户服务工单
     * 
     * @param id 客户服务工单主键
     * @return 客户服务工单
     */
    public CustomerServiceTicket selectCustomerServiceTicketById(Long id);

    /**
     * 查询客户服务工单列表
     * 
     * @param customerServiceTicket 客户服务工单
     * @return 客户服务工单集合
     */
    public List<CustomerServiceTicket> selectCustomerServiceTicketList(CustomerServiceTicket customerServiceTicket);

    /**
     * 新增客户服务工单
     * 
     * @param customerServiceTicket 客户服务工单
     * @return 结果
     */
    public int insertCustomerServiceTicket(CustomerServiceTicket customerServiceTicket);

    /**
     * 修改客户服务工单
     * 
     * @param customerServiceTicket 客户服务工单
     * @return 结果
     */
    public int updateCustomerServiceTicket(CustomerServiceTicket customerServiceTicket);

    /**
     * 删除客户服务工单
     * 
     * @param id 客户服务工单主键
     * @return 结果
     */
    public int deleteCustomerServiceTicketById(Long id);

    /**
     * 批量删除客户服务工单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomerServiceTicketByIds(Long[] ids);
} 