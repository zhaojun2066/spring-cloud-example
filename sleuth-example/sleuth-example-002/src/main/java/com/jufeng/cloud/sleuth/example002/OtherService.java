package com.jufeng.cloud.sleuth.example002;

import brave.Span;
import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-29 16:47
 **/

@Component
public class OtherService {

    @Autowired
    private Tracer tracer;


    public int getAge(){
        final Span span = tracer.nextSpan().name("getAge").start();
        try {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 这里时调用第三方 API 的代码
            return 10;
        } finally {
            span.finish();
        }
    }
}
