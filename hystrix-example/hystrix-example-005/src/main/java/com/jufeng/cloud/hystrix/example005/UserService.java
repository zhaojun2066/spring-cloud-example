package com.jufeng.cloud.hystrix.example005;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: spring-cloud-example
 * @description: 这里可以使用spring controller的 注解
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-04 09:40
 **/

@FeignClient(value = "hystrix-producer",fallback = UserCallBack.class)
public interface UserService {

    @GetMapping("/user/getName")
    String getName();

}
