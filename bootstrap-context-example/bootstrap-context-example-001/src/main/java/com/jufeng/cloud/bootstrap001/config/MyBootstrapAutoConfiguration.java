package com.jufeng.cloud.bootstrap001.config;

import com.jufeng.cloud.bootstrap001.server.BootStrapConfig;
import com.jufeng.cloud.bootstrap001.server.User;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring-cloud-example
 * @description: 自定义引导类
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-05 14:51
 **/

@Configuration
@EnableConfigurationProperties(BootStrapConfig.class)
public class MyBootstrapAutoConfiguration {

    @Bean
    public User user(BootStrapConfig bootStrapConfig){
        User jufeng = new User();
        jufeng.setName(bootStrapConfig.getName());
        return jufeng;
    }
}
