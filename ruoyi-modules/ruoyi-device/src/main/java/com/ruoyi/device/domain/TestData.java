package com.ruoyi.device.domain;

/**
 * 测试数据对象
 */
public class TestData
{
    private String id;
    private String name;
    
    public TestData() {}
    
    public TestData(String id, String name)
    {
        this.id = id;
        this.name = name;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
} 