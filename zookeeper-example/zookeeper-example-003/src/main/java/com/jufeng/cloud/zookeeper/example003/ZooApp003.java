package com.jufeng.cloud.zookeeper.example003;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-10-09 11:02
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ZooApp003 implements CommandLineRunner {

    @Autowired
    private HelloService helloService;





    public static void main(String[] args) {
        new SpringApplication().run(ZooApp003.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(helloService.home());
    }
}
