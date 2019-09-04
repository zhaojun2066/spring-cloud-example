package com.jufeng.cloud.openfeign008;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-04 16:43
 **/

public class GlobalFeignLoggerConfig {
    @Bean
    public Logger.Level level(){
        return Logger.Level.BASIC;
    }
}
