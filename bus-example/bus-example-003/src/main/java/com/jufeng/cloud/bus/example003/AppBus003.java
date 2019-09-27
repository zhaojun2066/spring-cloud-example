package com.jufeng.cloud.bus.example003;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-27 14:12
 **/

@SpringBootApplication
public class AppBus003 implements CommandLineRunner {


    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * 应用上下文（通过实现 ApplicationContextAware 实现自动装载）
     */
    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(AppBus003.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        //获取应用id
        String serviceInstanceId = applicationContext.getId();
        //新建 自定义事件 对象
        MyRemoteApplicationEvent event = new MyRemoteApplicationEvent(this, serviceInstanceId, "");
        //发布事件
        eventPublisher.publishEvent(event);
    }
}
