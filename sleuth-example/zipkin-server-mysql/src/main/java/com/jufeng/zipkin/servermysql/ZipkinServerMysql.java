package com.jufeng.zipkin.servermysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-29 09:56
 **/

@EnableDiscoveryClient
@EnableZipkinServer
@SpringBootApplication
public class ZipkinServerMysql {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerMysql.class,args);
    }
}
