package com.jufeng.cloud.eureka006;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-02 16:49
 **/
@RestController
public class UserController {


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/user/getName")
    public String getName(){
        String url = "http://eureka-producer/user/getName";
        return restTemplate.getForObject(url, String.class);
    }
}
