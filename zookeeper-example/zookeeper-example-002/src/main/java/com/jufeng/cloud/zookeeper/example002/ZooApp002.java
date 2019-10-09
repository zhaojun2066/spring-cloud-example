package com.jufeng.cloud.zookeeper.example002;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-10-09 11:02
 **/

@EnableDiscoveryClient
@SpringBootApplication
public class ZooApp002 implements CommandLineRunner {

    @Autowired
    private DiscoveryClient discoveryClient;



    @Bean
    public  RestTemplate restTemplate(){
        return new RestTemplate();
    }


   @Autowired
    private RestTemplate restTemplate;





    public static void main(String[] args) {
        new SpringApplication().run(ZooApp002.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<ServiceInstance> list = discoveryClient.getInstances("zookeeper-example-001");
        if (list!=null && !list.isEmpty()){
            ServiceInstance serviceInstance = list.get(0);
            System.out.println(serviceInstance.toString());
            System.out.println(serviceInstance.getHost());
            System.out.println(serviceInstance.getPort());
            System.out.println(serviceInstance.getScheme());
            System.out.println(serviceInstance.getUri());
           String s = restTemplate.getForObject(serviceInstance.getUri()+"/",String.class);
            System.out.println(s);

        }

    }
}
