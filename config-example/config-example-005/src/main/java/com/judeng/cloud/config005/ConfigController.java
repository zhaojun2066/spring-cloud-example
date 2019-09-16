package com.judeng.cloud.config005;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-05 17:27
 **/

@RefreshScope
@RestController
public class ConfigController {
    @Value("${name}")
    private String  name;


    @GetMapping("/say")
    public String say(){
        return this.name;
    }

}
