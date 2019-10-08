package com.jufeng.cloud.sleuth.example004;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-29 10:05
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class App004 {
    public static void main(String[] args) {
        SpringApplication.run(App004.class,args);
    }
}
