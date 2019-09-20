package com.jufeng.cloud.hystrix.example008;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-20 09:49
 **/

@Slf4j
@RestController
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping("/getUsers")
    public List<User> getUsers() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        List<User>  list =  userService.getUserByNames(Arrays.asList("a","b"));
        context.shutdown();
        return  list;
    }
}
