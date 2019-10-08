package com.jufeng.cloud.sleuth.example004;

import org.springframework.cloud.sleuth.annotation.TagValueResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-10-08 16:34
 **/

@Component
public class UserTagValueResolver implements TagValueResolver {

/*    @Bean(name = "myCustomTagValueResolver")
    public UserTagValueResolver tagValueResolver() {
        return new UserTagValueResolver();
    }*/

    // tag 返回 参数
    @Override
    public String resolve(Object parameter) {
        return (String)parameter;
    }
}
