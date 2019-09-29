package com.jufeng.cloud.sleuth.example002;

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
    private UserService userService;

    @GetMapping("/getUsername/{name}")
    public String gerUsername(@PathVariable("name") String name){
        return userService.getUsername(name);
    }
}
