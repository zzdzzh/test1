package com.ruoyi.device.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 客户服务工单对象 customer_service_tickets
 */
public class CustomerServiceTicket extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工单ID */
    private Long id;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String name;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 诉求内容 */
    @Excel(name = "诉求内容")
    private String content;

    /** 咨询类型：0-产品咨询 1-售后服务 2-投诉建议 3-其他 */
    @Excel(name = "咨询类型", readConverterExp = "0=产品咨询,1=售后服务,2=投诉建议,3=其他")
    private Integer consultType;

    /** 关联订单号 */
    @Excel(name = "关联订单号")
    private String relatedOrders;

    /** 工单状态：0-待处理 1-处理中 2-已完成 3-已关闭 */
    @Excel(name = "工单状态", readConverterExp = "0=待处理,1=处理中,2=已完成,3=已关闭")
    private Integer status;

    /** 微信用户openid */
    @Excel(name = "微信用户openid")
    private String openid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setConsultType(Integer consultType) 
    {
        this.consultType = consultType;
    }

    public Integer getConsultType() 
    {
        return consultType;
    }

    public void setRelatedOrders(String relatedOrders) 
    {
        this.relatedOrders = relatedOrders;
    }

    public String getRelatedOrders() 
    {
        return relatedOrders;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setOpenid(String openid) 
    {
        this.openid = openid;
    }

    public String getOpenid() 
    {
        return openid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("phone", getPhone())
            .append("content", getContent())
            .append("consultType", getConsultType())
            .append("relatedOrders", getRelatedOrders())
            .append("status", getStatus())
            .append("openid", getOpenid())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
} 