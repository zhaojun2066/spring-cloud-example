package com.jufeng.cloud.stream009;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-24 14:14
 **/

@SpringBootApplication
public class AppStream implements CommandLineRunner {

    @Autowired
    private SourceWithDynamicDestination service;

    public static void main(String[] args) {
        SpringApplication.run(AppStream.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        service.sendMsg("hello");
        service.sendMsg("hello");
        service.sendMsg("word");
        service.sendMsg("word");
        service.sendMsg("hha");
        service.sendMsg("hha");
    }
}
