package com.jufeng.cloud.stream010;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import java.nio.charset.StandardCharsets;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-25 15:43
 **/
public class MyCustomMessageConverter extends AbstractMessageConverter {


    protected MyCustomMessageConverter() {
        super(new MimeType("application","bar"));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return true; // 是否进行转换
    }

    //Convert the message payload from serialized form to an Object.
    // 接收的反序列化
    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        Object object = message.getPayload();
        if (object instanceof  byte[]){
            return new String ((byte[])object, StandardCharsets.UTF_8);
        }else {
            return object;
        }
    }

    // 相当于发送的序列化
    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint)  {
        if (payload != null) {
            if ((payload instanceof byte[])) {
                return payload;
            }
            else {
                return payload.toString().getBytes();
            }
        }
        else {
            return null;
        }
    }
}
