package com.jufeng.cloud.stream004;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.Map;

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


    public void sendMessage(String  message,String hello){
        System.out.println("发送-> " +message);
        source.output().send(MessageBuilder.withPayload(message).setHeader("hello",hello).build());
    }

    @StreamListener(value = Sink.INPUT,condition = "headers['hello']=='hello'")
    public void subscribeMessage(String message,@Header(name="hello") Object header){
        System.out.println("收到-> "+message);
        System.out.println("Header-> "+header);
    }

    @StreamListener(value = Sink.INPUT,condition = "headers['hello']=='word'")
    public void subscribeMessage2(String message,@Header(name="hello") Object header){
        System.out.println("收到2-> "+message);
        System.out.println("Header2-> "+header);
    }


}
