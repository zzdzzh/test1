package com.ruoyi.device.domain;

import java.util.Date;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import lombok.Data;

/**
 * 设备对象 device
 * 
 * @author ruoyi
 */
@Data
public class Device extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String name;

    /** 编号(IMEI) */
    @Excel(name = "设备编号")
    private String code;

    /** 密码 */
    private String secret;

    /** SN号 */
    @Excel(name = "SN号")
    private String sn;

    /** 卡号 */
    @Excel(name = "卡号")
    private String iccid;

    /** 父设备SN */
    private String parentSn;

    /** 类别 */
    @Excel(name = "类别")
    private String category;

    /** 组别ID */
    private Long groupId;

    /** 策略ID */
    private Long strategyId;

    /** 状态(启用/禁用) */
    @Excel(name = "状态")
    private String status;

    /** 终端 */
    private Integer terminal;

    /** 备注 */
    private String remark;

    // getter和setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ... 其他getter和setter方法
} 