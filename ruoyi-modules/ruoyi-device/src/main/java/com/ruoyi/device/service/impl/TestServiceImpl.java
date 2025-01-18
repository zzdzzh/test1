package com.ruoyi.device.service.impl;

import org.springframework.stereotype.Service;
import com.ruoyi.device.service.ITestService;
import com.ruoyi.device.domain.TestData;

/**
 * 测试服务实现
 * 
 * @author ruoyi
 */
@Service
public class TestServiceImpl implements ITestService
{
    /**
     * 获取测试消息
     * 
     * @param name 名称
     * @return 结果
     */
    @Override
    public String getMessage(String name)
    {
        return "Hello " + name + "! This is a message from TestService.";
    }

    @Override
    public int insertTest(TestData data)
    {
        // 这里实现新增逻辑
        return 1;
    }

    @Override
    public int updateTest(TestData data)
    {
        // 这里实现修改逻辑
        return 1;
    }

    @Override
    public int deleteTestById(String id)
    {
        // 这里实现删除逻辑
        return 1;
    }
} 