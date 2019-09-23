package com.jufeng.cloud.zuul.example005;

import org.springframework.beans.factory.annotation.Autowired;
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
public class Zuul005 {


    // 可以在你需要更新的地方调用 customRouteLocator.refresh()，就可以了
    @Autowired
    private CustomRouteLocator customRouteLocator;


    public static void main(String[] args) {
        SpringApplication.run(Zuul005.class, args);
    }
}
