package com.jufeng.cloud.stream005;

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

@EnableBinding({Source.class})
@SpringBootApplication
public class AppStream implements CommandLineRunner {

    @Autowired
    private Service service;

    public static void main(String[] args) {
        SpringApplication.run(AppStream.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        service.sendMessage("hello ","hello");
        service.sendMessage("word ","word");
        service.sendMessage("jufeng ","jufeng");
        service.sendMessage("dikou ","dikou");
        service.sendMessage("alibb ","alibb");
    }
}
