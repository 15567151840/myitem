package com.example.demo;

import com.example.demo.entity.Person;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
/**
 * 监听消息使用注解@RabbitListener必须要有@EnableRabbit
 *
 *
 * **/
class DemoApplicationTests {
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    void contextLoads() {
    }

    @Test
    public void sendMessageTest(){
        Person person = new Person();
        person.setName("张飞");
        person.setAge(18);
        person.setDescription("弗朗西斯好汉");

        String msg = "hello word";
     rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",person);
     System.out.println("消息发送成功");
    }
    @Test
    public  void createExchange(){
        //DirectExchange(String name,boolean durable,boolean autoDelete,Map<String,Object> arguments)
        //参数交换机的名字、是否持久化、是否删除、自定义的一些参数
        DirectExchange directExchange = new DirectExchange("hello-java-exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        System.out.print("交换机创建成功");
    }
    @Test
    public void createQueue(){
               //名字          是否持久化          排他                   是否自动删除
        //Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments)
        Queue queue = new Queue("hello-java-queue",true,false,false);
        amqpAdmin.declareQueue(queue);
        System.out.println("队列创建成功");

    }
    //测试交换机与队列绑定
    @Test
    public void createBinding(){
        //String destination:目的地
        //Binding.DestinationType destinationType 目的地的类型
        //String exchange 交换机
        //String routingKey 路由键
        //@Nullable Map<String, Object> arguments 自定义参数
        //将exchange交换机和destination:目的地进行绑定
        //Binding(String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments)
        Binding binding = new Binding("hello-java-queue",
                Binding.DestinationType.QUEUE,
                "hello-java-exchange",
                "hello.java",
                null);
        amqpAdmin.declareBinding(binding);
        System.out.println("绑定成功");


    }
}
