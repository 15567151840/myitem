package com.atguigu.eduuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.atguigu"})
@SpringBootApplication
@MapperScan("com.atguigu.eduuser.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class UserApplication {
    public static void main(String [] args){
        SpringApplication.run(UserApplication.class,args);
    }
}
