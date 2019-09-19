package com.jufeng.cloud.hystrix.example005;

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
        return "getName error....";
    }
}
