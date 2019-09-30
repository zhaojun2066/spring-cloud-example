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
        //Span span = tracer.nextSpan().name("getAge").kind(Span.Kind.SERVER).start();
        try {
          //  span.tag("hello","word");// 设置自定义key value
            Thread.sleep(5);
            // 这里时调用第三方 API 的代码
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
           // span.finish();
        }
        return 10;
    }
}
