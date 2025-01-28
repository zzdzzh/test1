package com.ruoyi.device.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 设备故障对象 device_fault
 * 
 * @author ruoyi
 */
public class DeviceFault extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;
    
    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceCode;
    
    /** 节点编号 */
    @Excel(name = "节点编号")
    private String nodeCode;
    
    /** 报警信息 */
    @Excel(name = "报警信息")
    private String alarmInfo;
    
    /** 报警时间 */
    @Excel(name = "报警时间")
    private String alarmTime;
    
    /** 组别 */
    @Excel(name = "组别")
    private String groupName;
    
    /** 类别 */
    @Excel(name = "类别")
    private String category;
    
    /** 状态（离线/在线） */
    @Excel(name = "状态")
    private String status;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 