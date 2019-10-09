package com.jufeng.cloud.sleuth.example004;

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


    @NewSpan("getUser")
    @ContinueSpan(log = "j-user")
    public String getUser(@SpanTag(key = "name", resolver = UserTagValueResolver.class) String name){
        return "jufeng-gege";
    }


    public String sport(String name){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // do some work
              // tracer.currentSpan().name("hahha");
                System.out.println(" sport: "+name);
                tracer.currentSpan().finish();
            }

            @Override
            public String toString() {
                return "spanNameFromToStringMethod";
            }
        };
        SpanNamer spanNamer = new DefaultSpanNamer();
        Runnable traceRunnable = new TraceRunnable(tracing,spanNamer , runnable,
                "calculateTax");
        Thread thread = new Thread(traceRunnable);
        thread.start();
        return name;
    }
}
