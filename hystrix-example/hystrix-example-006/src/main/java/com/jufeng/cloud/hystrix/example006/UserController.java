package com.jufeng.cloud.hystrix.example006;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    public String getUsers() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
       Future<User> f1= userService.getUserByName("ali");
       Future<User> f2= userService.getUserByName("ajun");
       Future<User> f3= userService.getUserByName("chaozi");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       User u1 =f1.get();
       User u2 = f2.get();
       User u3 =f3.get();



        Future<User> f4= userService.getUserByName("kaikai");
        User u4 = f4.get();

        log.info("u1: {}" ,u1);
        log.info("u2: {}" ,u2);
        log.info("u3: {}" ,u3);
        log.info("u4: {}" ,u4);
        context.shutdown();
        return "success";
    }
}
