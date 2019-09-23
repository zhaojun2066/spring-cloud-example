package com.jufeng.cloud.zuul.exampleserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-02 16:43
 **/
@Slf4j
@RestController
public class UserController {

    @GetMapping("/user/getName")
    public String getUsername(){
        log.info("getUsername ....");
        return "JUFENG";
    }

    @GetMapping("/user/{what}")
    public String say(@PathVariable("what") String what){
        return "say: " + what;
    }

    @GetMapping("/user/hello")
    public String hello(@RequestParam("what") String what){
        return "hello: " + what;
    }
}
