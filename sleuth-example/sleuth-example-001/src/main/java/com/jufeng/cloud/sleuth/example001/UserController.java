package com.jufeng.cloud.sleuth.example001;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-29 10:06
 **/

@RestController
public class UserController {


    @GetMapping("/getUsername")
    public String gerUsername(String name){
        return "hello "+name;
    }
}
