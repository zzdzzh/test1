package com.ruoyi.device.service;

import java.util.List;
import com.ruoyi.device.domain.DeviceFault;

/**
 * 设备故障Service接口
 * 
 * @author ruoyi
 */
public interface IDeviceFaultService 
{
    /**
     * 查询故障
     * 
     * @param id 故障主键
     * @return 故障
     */
    public DeviceFault selectDeviceFaultById(Long id);

    /**
     * 查询故障列表
     * 
     * @param deviceFault 故障
     * @return 故障集合
     */
    public List<DeviceFault> selectDeviceFaultList(DeviceFault deviceFault);

    /**
     * 新增故障
     * 
     * @param deviceFault 故障
     * @return 结果
     */
    public int insertDeviceFault(DeviceFault deviceFault);

    /**
     * 修改故障
     * 
     * @param deviceFault 故障
     * @return 结果
     */
    public int updateDeviceFault(DeviceFault deviceFault);

    /**
     * 批量删除故障
     * 
     * @param ids 需要删除的故障主键集合
     * @return 结果
     */
    public int deleteDeviceFaultByIds(Long[] ids);

    /**
     * 删除故障信息
     * 
     * @param id 故障主键
     * @return 结果
     */
    public int deleteDeviceFaultById(Long id);
} 