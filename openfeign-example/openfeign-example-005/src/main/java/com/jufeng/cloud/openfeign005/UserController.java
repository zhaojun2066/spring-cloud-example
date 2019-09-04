package com.jufeng.cloud.openfeign005;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-02 16:49
 **/
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/user/getName")
    public String getName(){
        return userService.getName();
    }

}
