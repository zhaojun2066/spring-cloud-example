package com.jufeng.cloud.stream003;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-24 15:43
 **/
public interface MyInputOutput {
    String OUTPUT = "myOutput";

    @Output(MyInputOutput.OUTPUT)
    MessageChannel myOutput();


    String INPUT = "myInput";

    @Input(MyInputOutput.INPUT)
    SubscribableChannel myInput();
}
