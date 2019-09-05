package com.jufeng.cloud.resttemplateoo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-08-29 18:45
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaConsumerApplication {


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder templateBuilder){
        return templateBuilder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerApplication.class, args);
    }
}
