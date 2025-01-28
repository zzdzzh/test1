package com.ruoyi.device.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.device.mapper.DeviceFaultMapper;
import com.ruoyi.device.domain.DeviceFault;
import com.ruoyi.device.service.IDeviceFaultService;

/**
 * 设备故障Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class DeviceFaultServiceImpl implements IDeviceFaultService {
    @Autowired
    private DeviceFaultMapper deviceFaultMapper;

    /**
     * 查询故障
     * 
     * @param id 故障主键
     * @return 故障
     */
    @Override
    public DeviceFault selectDeviceFaultById(Long id) {
        return deviceFaultMapper.selectDeviceFaultById(id);
    }

    /**
     * 查询故障列表
     * 
     * @param deviceFault 故障
     * @return 故障
     */
    @Override
    public List<DeviceFault> selectDeviceFaultList(DeviceFault deviceFault) {
        return deviceFaultMapper.selectDeviceFaultList(deviceFault);
    }

    /**
     * 新增故障
     * 
     * @param deviceFault 故障
     * @return 结果
     */
    @Override
    public int insertDeviceFault(DeviceFault deviceFault) {
        return deviceFaultMapper.insertDeviceFault(deviceFault);
    }

    /**
     * 修改故障
     * 
     * @param deviceFault 故障
     * @return 结果
     */
    @Override
    public int updateDeviceFault(DeviceFault deviceFault) {
        return deviceFaultMapper.updateDeviceFault(deviceFault);
    }

    /**
     * 批量删除故障
     * 
     * @param ids 需要删除的故障主键
     * @return 结果
     */
    @Override
    public int deleteDeviceFaultByIds(Long[] ids) {
        return deviceFaultMapper.deleteDeviceFaultByIds(ids);
    }

    /**
     * 删除故障信息
     * 
     * @param id 故障主键
     * @return 结果
     */
    @Override
    public int deleteDeviceFaultById(Long id) {
        return deviceFaultMapper.deleteDeviceFaultById(id);
    }
} 