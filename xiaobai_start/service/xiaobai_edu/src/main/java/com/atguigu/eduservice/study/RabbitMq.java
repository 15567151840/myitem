package com.atguigu.eduservice.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 1.如何创建exchange、Queue、Binding
 *   1)使用AmqpAdmin进行创建
 * 2.如何收发消息
 *
 * **/
public class RabbitMq {
    @Autowired
    private AmqpAdmin amqpAdmin;
    public static void main(String [] args){

    }

    @Test
    public  void createExchange(){
        //DirectExchange(String name,boolean durable,boolean autoDelete,Map<String,Object> arguments)
        //参数交换机的名字、是否持久化、是否删除、自定义的一些参数
        DirectExchange directExchange = new DirectExchange("hello-java-exchange",true,false);
          amqpAdmin.declareExchange(directExchange);
          System.out.print("交换机创建成功");
        }
}
