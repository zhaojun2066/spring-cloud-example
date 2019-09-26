package com.jufeng.cloud.stream011;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-example
 * @description:  手动提交
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-26 11:27
 **/

@Component
public class Consumer {

    @Autowired
    private Source source;

    public void sendMessage(String  message){
        System.out.println("发送-> " +message);
        source.output().send(MessageBuilder.withPayload(message).build());
    }


    @StreamListener(Sink.INPUT)
    public void process(Message<?> message) {
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        System.out.println("收到11-> "+ new String((byte[])message.getPayload()));
        if (acknowledgment != null) {
            System.out.println("Acknowledgment provided");
            acknowledgment.acknowledge();
        }
    }
}
