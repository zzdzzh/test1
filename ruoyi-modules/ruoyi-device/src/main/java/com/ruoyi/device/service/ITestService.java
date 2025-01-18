package com.ruoyi.device.service;

import com.ruoyi.device.domain.TestData;

/**
 * 测试服务接口
 * 
 * @author ruoyi
 */
public interface ITestService
{
    /**
     * 获取测试消息
     * 
     * @param name 名称
     * @return 结果
     */
    public String getMessage(String name);

    /**
     * 新增测试数据
     * 
     * @param data 测试数据
     * @return 结果
     */
    public int insertTest(TestData data);

    /**
     * 修改测试数据
     * 
     * @param data 测试数据
     * @return 结果
     */
    public int updateTest(TestData data);

    /**
     * 删除测试数据
     * 
     * @param id 测试数据ID
     * @return 结果
     */
    public int deleteTestById(String id);
} 