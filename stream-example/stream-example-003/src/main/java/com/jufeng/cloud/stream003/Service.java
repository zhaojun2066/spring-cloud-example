package com.jufeng.cloud.stream003;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-24 14:59
 **/

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private MyInputOutput myInputOutput;



    public void sendMessage(String  message){
        System.out.println("发送3-> " +message);
        myInputOutput.myOutput().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(MyInputOutput.OUTPUT)
    public void subscribeMessage(String message){
        System.out.println("收到3-> "+message);
    }


}
