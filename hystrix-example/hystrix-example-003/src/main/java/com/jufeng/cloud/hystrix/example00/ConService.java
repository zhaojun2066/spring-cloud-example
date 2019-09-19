package com.jufeng.cloud.hystrix.example00;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
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
            threadPoolKey = "sayThread",
            commandProperties = {
                    @HystrixProperty(name = "requestCache.enabled",value = "true"),
            },
            fallbackMethod = "getUsernameFallback")
    //@CacheResult的指定了cacheKeyMethod属性，则@CacheKey注解无效
    public String say(/*@CacheKey */String hello){
        String s= restTemplate.getForObject("http://hystrix-producer/say/"+hello, String.class);
        return s;
    }

    // 调用此方法的时候，就会删除本次线程内的cache，注意 commandKey 必须和CacheResult修饰的HystrixCommand的 commandKey一样
    @CacheRemove(commandKey = "say",cacheKeyMethod = "getRomoveKey")
    @HystrixCommand(commandKey = "say",
            threadPoolKey = "sayThread")
    public void update(String hello){

    }


    public String getUsernameFallback(String name){
        return "getUsernameFallback....";
    }



    public String getKey(String hello){
        return hello;
    }

    public String getRomoveKey(String hello){
        return hello;
    }
}
