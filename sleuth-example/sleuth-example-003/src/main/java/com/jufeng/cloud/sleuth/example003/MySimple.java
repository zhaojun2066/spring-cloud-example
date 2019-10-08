package com.jufeng.cloud.sleuth.example003;


import brave.SpanCustomizer;
import brave.Tracer;
import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-30 15:46
 **/
public class MySimple extends Sampler {


    // 当前线程安全的
    @Autowired
    SpanCustomizer span;

    @Autowired
    private Tracer tracer;

    @Override
    public boolean isSampled(long traceId) {
        return true;
    }
}
