package com.jufeng.cloud.bootstrap001.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-05 14:50
 **/

@Data
@ConfigurationProperties("jufeng")
public class BootStrapConfig {
    private String name;
}
