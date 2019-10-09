package com.jufeng.cloud.sleuth.example005;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * @program: spring-cloud-example
 * @description:  异步servlet支持，会创建一个新的span
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-29 10:12
 **/

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/getUser")
    public Callable<User> getAge(){
        this.userService.getAge();
        return (Callable) () -> userService.getUser();
    }





}
