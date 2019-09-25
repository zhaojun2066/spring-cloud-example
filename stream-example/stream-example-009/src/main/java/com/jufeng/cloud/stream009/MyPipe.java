package com.jufeng.cloud.stream009;

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


    String helloInput="helloInput";
    @Input(MyPipe.helloInput)
    SubscribableChannel helloInput();

    String wordInput="wordInput";
    @Input(MyPipe.wordInput)
    SubscribableChannel wordInput();



}
