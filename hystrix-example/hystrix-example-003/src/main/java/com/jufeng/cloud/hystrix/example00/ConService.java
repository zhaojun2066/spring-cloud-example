package com.jufeng.cloud.hystrix.example00;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-18 15:29
 **/

@org.springframework.stereotype.Service
public class ConService {

    @Autowired
    private RestTemplate restTemplate;




    @CacheResult(cacheKeyMethod="getKey") // 当 requestCache.enabled =true 才启作用，用于缓存返回结果
    @HystrixCommand(
            commandKey = "say",
            commandProperties = {
                    @HystrixProperty(name = "requestCache.enabled",value = "true"),
            },
            fallbackMethod = "getUsernameFallback")
    //@CacheResult的指定了cacheKeyMethod属性，则@CacheKey注解无效，这里指定了name 作为key
    public String say(/*@CacheKey */String hello){
        String s= restTemplate.getForObject("http://hystrix-producer/say/"+hello, String.class);
        return s;
    }


    public String getUsernameFallback(String name){
        return "getUsernameFallback....";
    }



    public String getKey(String hello){
        return hello;
    }
}
