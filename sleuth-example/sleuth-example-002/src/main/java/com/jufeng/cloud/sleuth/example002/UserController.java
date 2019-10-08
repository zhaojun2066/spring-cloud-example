package com.jufeng.cloud.sleuth.example002;

import brave.Span;
import brave.SpanCustomizer;
import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-29 10:12
 **/

@RestController
public class UserController {

    @Autowired
    private OtherService otherService;

    @Autowired
    private UserService userService;
    @Autowired
    private Tracer tracer;


    // 当前线程安全的
    @Autowired
    SpanCustomizer spans;

    @GetMapping("/user/{name}")
    public String gerUsername(@PathVariable("name") String name){
        // 当前controller getUsername 的span
        spans.annotate("aaaa.started");
        spans.tag("aaaa","aaaa");
        spans.annotate("aaaa.end");
        String s = userService.getUsername(name);
        Span span = tracer.nextSpan().name("getAge").kind(Span.Kind.CLIENT).start();
        try {
            span.tag("username","hello");// 自定义key value
            span.annotate("自定义操作--开始调用getAge");
            otherService.getAge();
            span.annotate("自定义操作--结束调用getAge");
        }catch (Exception e){
            span.tag("npe",e.getMessage());
            span.error(e);// 如果有err 可以将err 写入
        }finally {
            span.finish();
        }
        spans.annotate("bbb.started");
        spans.tag("bbb","vvvv");
        spans.annotate("bbb.end");
        return s;
    }
}
