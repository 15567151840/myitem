package com.atguigu.calendar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.atguigu"})
@SpringBootApplication
@MapperScan("com.atguigu.calendar.mapper")
@EnableDiscoveryClient
public class CalenderApplication {
    public static void main(String [] args){
        SpringApplication.run(CalenderApplication.class,args);
    }
}