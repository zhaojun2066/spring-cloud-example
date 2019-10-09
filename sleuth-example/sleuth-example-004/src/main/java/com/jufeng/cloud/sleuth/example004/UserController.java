package com.jufeng.cloud.sleuth.example004;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    private UserService userService;

    @GetMapping("/user/getAge")
    public Integer getAge(){
        this.userService.getName("jufeng");
        this.userService.getUser("JuFeng");
        this.userService.sport("run...");
        return userService.getAge();
    }

    @GetMapping("/other/getOther")
    public Integer getOther(){
       return 100;
    }




}
