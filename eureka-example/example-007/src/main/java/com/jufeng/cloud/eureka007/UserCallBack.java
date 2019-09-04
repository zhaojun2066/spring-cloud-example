package com.jufeng.cloud.eureka007;

import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-04 10:37
 **/

@Component
public class UserCallBack implements UserService {
    @Override
    public String getName() {
        return "error getName";
    }

    @Override
    public String say(String what) {
        System.out.println("==================error say ");
        return "error say " + what;
    }

    @Override
    public String hello(String what) {
        return "error hello " + what;
    }
}
