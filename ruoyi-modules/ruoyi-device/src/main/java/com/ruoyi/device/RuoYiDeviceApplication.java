package com.ruoyi.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;


/**
 * 设备管理模块
 * 
 * @author ruoyi
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
@MapperScan("com.ruoyi.device.mapper")
public class RuoYiDeviceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiDeviceApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  设备管理模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
} 