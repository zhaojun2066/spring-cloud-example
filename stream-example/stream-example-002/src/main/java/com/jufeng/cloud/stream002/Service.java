package com.jufeng.cloud.stream002;

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


    public void sendMessage(String  message){
        System.out.println("发送-> " +message);
        source.output().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(Sink.INPUT)
    public void subscribeMessage(String message,@Header(name="contentType") Object header,@Headers Map<String,Object> headers){
        System.out.println("收到-> "+message);
        System.out.println("Header-> "+header);
        System.out.println("headers-> "+headers);
    }


}
