package com.jufeng.cloud.openfeign010;

import feign.Request;
import org.springframework.context.annotation.Bean;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-04 16:43
 **/

public class UserServerFeignConfig {
    @Bean
    public Request.Options feignRequestOptions() {
        return new Request.Options(8000, 3000);
    }


}
