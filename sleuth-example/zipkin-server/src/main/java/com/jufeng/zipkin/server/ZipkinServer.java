package com.jufeng.zipkin.server;

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
public class ZipkinServer {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinServer.class,args);
    }
}
