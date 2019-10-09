package com.jufeng.cloud.zookeeper.example000;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-10-09 11:02
 **/

@SpringBootApplication
@RestController
public class ZooApp000 {

    @RequestMapping("/")
    public String home() {
        return "Hello world 000";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZooApp000.class).web(true).run(args);
    }
}
