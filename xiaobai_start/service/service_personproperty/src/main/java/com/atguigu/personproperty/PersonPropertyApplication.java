package com.atguigu.personproperty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.atguigu"})
@SpringBootApplication
@MapperScan("com.atguigu.personproperty.mapper")
@EnableDiscoveryClient
public class PersonPropertyApplication {
    public static void main(String [] args){
        SpringApplication.run(PersonPropertyApplication.class,args);
    }

}
