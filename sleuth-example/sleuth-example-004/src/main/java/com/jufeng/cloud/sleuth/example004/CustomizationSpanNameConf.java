package com.jufeng.cloud.sleuth.example004;

import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring-cloud-example
 * @description: 自定义span name ，可以给每个span name 起作用
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-10-08 17:43
 **/
@Configuration
public class CustomizationSpanNameConf {

    @Bean
    SpanAdjuster adjusterOne() {
        return span -> span.toBuilder().name("foo->"+span.name()).build();
    }

    @Bean
    SpanAdjuster adjusterTwo() {
        return span -> span.toBuilder().name(span.name() + "<-bar").build();
    }
}
