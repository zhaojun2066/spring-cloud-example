package com.jufeng.cloud.bootstrap001.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-05 14:52
 **/

@SpringBootApplication()
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
        User jufeng = applicationContext.getBean(User.class);
        System.out.println(jufeng.getName());
    }
}
