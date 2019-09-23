package com.jufeng.cloud.zuul.example001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-23 14:10
 **/
@EnableZuulProxy
@SpringBootApplication
public class Zuul001 {

    public static void main(String[] args) {
        SpringApplication.run(Zuul001.class, args);
    }
}
