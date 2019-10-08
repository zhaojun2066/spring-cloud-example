package com.jufeng.cloud.sleuth.example003;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-30 16:02
 **/
@Configuration
public class MySimpleConf {

    // 覆盖 ProbabilityBasedSampler
    @Bean
    public Sampler sampler(){
        return new MySimple();
    }
}
