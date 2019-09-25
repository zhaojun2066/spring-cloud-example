package com.jufeng.cloud.stream008;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.SendTo;

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
    @SendTo(MyPipe.myInput)
    public String subscribeMessage(String message){
        System.out.println("收到-> "+message);
        return "hello=> " + message+ " return.";
    }

    @StreamListener(MyPipe.myInput)
    public void getSendTo(String msg){
        System.out.println("myInput -> "+msg);
    }


}
