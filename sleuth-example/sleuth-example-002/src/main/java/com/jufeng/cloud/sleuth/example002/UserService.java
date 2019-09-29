package com.jufeng.cloud.sleuth.example002;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-29 10:12
 **/


@FeignClient("sleuth-example-001")
public interface UserService {

    @GetMapping("/getUsername")
    public String getUsername(@RequestParam("name") String name);
}
