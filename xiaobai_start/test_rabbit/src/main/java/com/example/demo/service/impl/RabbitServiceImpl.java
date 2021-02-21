package com.example.demo.service.impl;

import com.example.demo.entity.Person;
import com.example.demo.service.RabbitService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.channels.Channel;

/**
 * @RabbitListener(标在类上)
 * 结合@RabbitHandler
 * 可以做到重载区分
 * **/
@RabbitListener(queues = {"hello-java-queue"})
@Service
public class RabbitServiceImpl implements RabbitService {

    //@RabbitListener(queues = {"hello-java-queue"})//这个注解可以在类上
    @RabbitHandler//结合在类上的注解使用
    public void recieveMessage(Message message,
                               Channel channel) throws InternalError{
        byte[] body = message.getBody();
        MessageProperties properties = message.getMessageProperties();
        System.out.println("接收到消息...."+message+"===>类型"+message.getClass());

        //签收货物非批量模式
        //channel.basicAsk() 版本不对没有此方法

    }
}
