package com.jufeng.cloud.zookeeper.example003;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-10-09 14:41
 **/
@FeignClient("zookeeper-example-001")
public interface HelloService {

    @GetMapping("/")
    String home();
}
