package com.ruoyi.device.mapper;

import java.util.List;
import com.ruoyi.device.domain.DeviceFault;

/**
 * 设备故障Mapper接口
 * 
 * @author ruoyi
 */
public interface DeviceFaultMapper 
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
     * 删除故障
     * 
     * @param id 故障主键
     * @return 结果
     */
    public int deleteDeviceFaultById(Long id);

    /**
     * 批量删除故障
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceFaultByIds(Long[] ids);
} 