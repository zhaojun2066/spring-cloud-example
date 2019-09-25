package com.jufeng.cloud.stream005;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.Header;

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


    public void sendMessage(String  message,String hello){
        System.out.println("发送-> " +message);
        source.output().send(MessageBuilder.withPayload(message).setHeader("partitionKey",hello).build());
    }

}
