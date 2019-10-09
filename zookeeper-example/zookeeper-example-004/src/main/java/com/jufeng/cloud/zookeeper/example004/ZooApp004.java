package com.jufeng.cloud.zookeeper.example004;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-10-09 11:02
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class ZooApp004 implements CommandLineRunner {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/")
    public String home() {
        return helloService.home();
    }


    public static void main(String[] args) {
        new SpringApplication().run(ZooApp004.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
      /*  System.out.println(helloService.home());
        System.out.println(helloService.home());
        System.out.println(helloService.home());
        System.out.println(helloService.home());
        System.out.println(helloService.home());
        System.out.println(helloService.home());*/
    }
}
