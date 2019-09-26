package com.jufeng.cloud.stream010;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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


@EnableBinding({Source.class, Sink.class})
@SpringBootApplication
public class AppStream implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppStream.class,args);
    }

    @Autowired
    @Qualifier("services")
    private Services services;


    @Override
    public void run(String... args) throws Exception {
        services.sendMessage("hello stream ");
    }
}
