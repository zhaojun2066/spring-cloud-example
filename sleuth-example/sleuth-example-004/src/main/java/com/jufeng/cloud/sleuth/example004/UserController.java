package com.jufeng.cloud.sleuth.example004;

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

    @GetMapping("/user/getAge")
    public Integer getAge(){
        this.otherService.getName("jufeng");
        this.otherService.getUser("JuFeng");
        return otherService.getAge();
    }

    @GetMapping("/other/getOther")
    public Integer getOther(){
       return 100;
    }




}
