package com.jufeng.cloud.zuul.example006;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-23 14:10
 **/
@EnableZuulProxy
@SpringBootApplication
public class Zuul006 {

    public static void main(String[] args) {
        SpringApplication.run(Zuul006.class, args);
    }
}
