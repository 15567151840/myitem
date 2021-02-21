package com.example.demo.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRabbitConfig {
    private RabbitTemplate rabbitTemplate;
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    /***
     *  定制RabbitTemplate
     *  @PostConstruct // MyRabbitConfig对象创建完成以后，调用这个方法
     *  以上两个都是生产者确认
     *
     *  消费端确认(保证每个消息被正确消费、此时才可以broker删除这个消息)
     *    1、默认是自动确认的、只要消息收到，客户端会自动确认、服务端就会移出这个消息
     *      问题：
     *        我们收到很多消息，自动回复给服务器ack。只有一个消息处理成功了。宕机了。发生消息丢失
     *        我们可以手动确认.只要我们没有明确告诉MQ货物被签收。没有ACK,消息就一直是unacked状态，即使Consumer宕机，消息也不会丢失
     *        会重新变为read状态，准备下一次有新的Consumer进来发送他
     *
     *
     */


    public void initRabbitTemplate(){
        //设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 只要消息抵达Broker ack就会是true
             * @param correlationData 当前消息唯一关联数据（唯一id）
             * @param ack 消息是否成功收到
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm...correlationData[{}],ack[{}],cause[{}]"+correlationData+ ack+cause);
            }
        });

        // 设置消息抵达队列Queue的确认回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递给指定的队列，就触发这个失败回调
             * @param message 投递失败的消息的详细信息
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange 发送时的交换机
             * @param routingKey 发送时的路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

               System.out.println("Fail Message[{}],replyCode[{}]"+ message+replyCode);
            }
        });
    }
}
