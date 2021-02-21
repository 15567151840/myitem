package com.atguigu.commen;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.atguigu"})
@SpringBootApplication
@MapperScan("com.atguigu.commen.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class CommApplication {
    public static void main(String [] args){
        SpringApplication.run(CommApplication.class,args);
    }
}
