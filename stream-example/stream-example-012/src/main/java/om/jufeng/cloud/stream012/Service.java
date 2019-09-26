package om.jufeng.cloud.stream012;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.Map;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-24 14:59
 **/
@Slf4j
@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private Source source;

    @Autowired
    private Sink sink;


    public void sendMessage(String  message){
        log.info("发送12-> " +message);
        source.output().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(Sink.INPUT)
    public void subscribeMessage(String message,@Header(name="contentType") Object header,@Headers Map<String,Object> headers){
        log.info("收到12-> "+message);
        throw new RuntimeException("Message consumer failed!");
    }


    /**
     * 消息消费失败的降级处理逻辑
     * topic.group.errors
     * @param message
     */
    @ServiceActivator(inputChannel = "test12.group-aa.errors")
    public void error(Message<?> message) {
        log.info("Message consumer failed, call fallback!");
    }


}
