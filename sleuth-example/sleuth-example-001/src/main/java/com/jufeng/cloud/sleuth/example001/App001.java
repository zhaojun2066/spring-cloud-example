package com.jufeng.cloud.sleuth.example001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-29 10:05
 **/

@EnableDiscoveryClient
@SpringBootApplication
public class App001 {
    public static void main(String[] args) {
        SpringApplication.run(App001.class,args);
    }
}
