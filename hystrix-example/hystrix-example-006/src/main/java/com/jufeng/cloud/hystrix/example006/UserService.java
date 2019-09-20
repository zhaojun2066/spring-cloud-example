package com.jufeng.cloud.hystrix.example006;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-20 09:29
 **/

@Slf4j
@Service
public class UserService {


    @Autowired
    private RestTemplate restTemplate;


    @HystrixCollapser(batchMethod = "getUserByNames",
           scope=com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
    collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "100")
    })
    public Future<User> getUserByName(String name){
        log.info("单个" );
        return null;
    }

    /*@HystrixCommand
    public List<User> getUsers(List<String> names){

        // 下面应该实际的网路请求
        List<User> list = new ArrayList<>();
        if (names!=null){
            list=  names.stream().map(x->{
                User user = new User();
                user.setName(x);
                return user;
            }).collect(Collectors.toList());
        }
        log.info("批量数据返回信息："+list.toString());
        return list;
    }
*/

    @HystrixCommand
    public List<User> getUserByNames(List<String> names){
        System.out.println("getUsers---------"+names+"Thread.currentThread().getName():" + Thread.currentThread().getName());
        // 下面应该实际的网路请求
        List<User> s= restTemplate.getForObject("http://hystrix-producer/getUsers?names={1}", List.class,StringUtils.join(names,","));
        log.info("批量数据返回信息："+ s.toString());
        return s;
    }




}
