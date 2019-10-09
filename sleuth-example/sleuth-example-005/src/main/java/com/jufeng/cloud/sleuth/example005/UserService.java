package com.jufeng.cloud.sleuth.example005;

import brave.Tracer;
import brave.Tracing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.DefaultSpanNamer;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.annotation.ContinueSpan;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.cloud.sleuth.instrument.async.TraceRunnable;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-29 16:47
 **/

@Component
public class UserService {

    @Autowired
    private Tracing tracing;

    @Autowired
    private Tracer tracer;

    @NewSpan("getUser")
    public User getUser(){
        User user = new User();
        user.setAge(10);
        user.setName("jufeng");
        return user;
    }


    @NewSpan("getAge")
    @ContinueSpan(log = "hello")
    public int getAge(){
        return 10;
    }

    @NewSpan("getName")
    @ContinueSpan(log = "k-name")
    public String getName(@SpanTag(key = "name",expression = "'hello' + ' word'" ) String name){
        return "jufeng";
    }


}
