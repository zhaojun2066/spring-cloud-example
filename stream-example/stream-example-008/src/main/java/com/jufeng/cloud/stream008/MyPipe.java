package com.jufeng.cloud.stream008;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-25 14:19
 **/
public interface MyPipe {
    String myInput="myInput";
    @Input(MyPipe.myInput)
    SubscribableChannel myInput();
}
