package com.jufeng.cloud.stream010;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-24 14:59
 **/

@Component("services")
public class Services {

    @Autowired
    private Source source;

    public void sendMessage(String  message){
        System.out.println("发送-> " +message);
        source.output().send(MessageBuilder.withPayload(message)
               .setHeader(MessageHeaders.CONTENT_TYPE,"application/bar").build());
    }

    @StreamListener(Sink.INPUT)
    public void subscribeMessage(String message,@Header(name="contentType") Object header){
        System.out.println("收到-> "+message);
        System.out.println("Header-> "+header);
    }


}
