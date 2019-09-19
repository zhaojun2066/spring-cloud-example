package com.jufeng.cloud.hystrix.example001;

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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("getUsername ....");
        return "JUFENG";
    }

    @GetMapping("/say/{what}")
    public String say(@PathVariable("what") String what){
        log.info("say ....");
        return "say: " + what;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam("what") String what){
        return "hello: " + what;
    }
}
