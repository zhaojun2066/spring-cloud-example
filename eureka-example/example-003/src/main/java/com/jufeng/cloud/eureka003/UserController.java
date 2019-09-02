package com.jufeng.cloud.eureka003;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-02 16:43
 **/

@RestController
public class UserController {

    @GetMapping("/user/getName")
    public String getUsername(){
        return "JUFENG";
    }
}
