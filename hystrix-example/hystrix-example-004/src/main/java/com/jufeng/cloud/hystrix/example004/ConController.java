package com.jufeng.cloud.hystrix.example004;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-02 16:49
 **/
@RestController
public class ConController {

    @Autowired
    private ConService conService;


    @GetMapping("/user/getName")
    public String getUsername(){
        //初始化Hystrix请求上下文 ,下面用于解决： Request caching is not available. Maybe you need to initialize the HystrixRequestContext?
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        String s= conService.getUsername("what");
       context.shutdown();
        return s;
    }
}
