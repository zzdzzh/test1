package com.ruoyi.device.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/hello")
public class HelloController
{
    /**
     * hello world
     */
    @GetMapping("/world")
    public String helloWorld()
    {
        return "Hello World!";
    }
} 