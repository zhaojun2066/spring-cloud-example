package com.jufeng.cloud.openfeign006;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/say/{what}")
    public String say(@PathVariable("what") String what){
        return userService.say(what);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam("what") String what){
        return userService.hello(what);
    }
}
