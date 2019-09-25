package com.jufeng.cloud.stream009;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-25 14:38
 **/
@Slf4j
@EnableBinding(MyPipe.class)
@Component
public class SourceWithDynamicDestination {

    @Autowired
    private BinderAwareChannelResolver resolver;

    public void sendMsg(String msg){
        MessageChannel messageChannel = null;
        if (StringUtils.isEmpty(msg)){
            messageChannel= resolver.resolveDestination("myInput");
        }else if ("hello".equals(msg)){
            messageChannel= resolver.resolveDestination("helloInput");
        }else if ("word".equals(msg)){
            messageChannel= resolver.resolveDestination("wordInput");
        }else {
            messageChannel= resolver.resolveDestination("myInput");
        }
        messageChannel.send(MessageBuilder.withPayload("hello").build());
    }



    @StreamListener(MyPipe.myInput)
    public void myInput(String msg){
        log.info("myInput -> "+msg);
    }


    @StreamListener(MyPipe.helloInput)
    public void helloInput(String msg){
        log.info("helloInput -> "+msg);
    }

    @StreamListener(MyPipe.wordInput)
    public void wordInput(String msg){
        log.info("wordInput -> "+msg);
    }
}
