package com.jufeng.cloud.eureka003;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-08-29 18:45
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaProducerApplication.class, args);
    }
}
