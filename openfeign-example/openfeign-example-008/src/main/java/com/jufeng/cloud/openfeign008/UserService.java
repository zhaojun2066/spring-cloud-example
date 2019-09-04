package com.jufeng.cloud.openfeign008;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: spring-cloud-example
 * @description: 这里可以使用spring controller的 注解
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-04 09:40
 **/

@FeignClient(value = "eureka-producer",configuration = UserServerFeignConfig.class)
public interface UserService {

    @GetMapping("/user/getName")
    String getName();

    @GetMapping("/say/{what}")
    String say(@PathVariable("what") String what);

    @GetMapping("/hello")
    String hello(@RequestParam("what") String what);
}
