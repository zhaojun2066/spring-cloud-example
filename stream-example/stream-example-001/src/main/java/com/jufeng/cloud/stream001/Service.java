package com.jufeng.cloud.stream001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.filter.MessageFilter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-24 14:59
 **/

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private Source source;

    @Autowired
    private Sink sink;


    public void sendMessage(String  message){
        System.out.println("发送-> " +message);
        source.output().send(MessageBuilder.withPayload(message).build());
    }

    public void subscribeMessage(){
        sink.input().subscribe(message -> {
            System.out.println("收到-> "+ new String((byte[])message.getPayload()));
        });
    }


}
