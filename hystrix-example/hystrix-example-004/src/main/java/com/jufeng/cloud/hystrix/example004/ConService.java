package com.jufeng.cloud.hystrix.example004;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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




    @HystrixCommand(
            commandKey = "say",
            threadPoolKey = "sayThread",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.strategy",value = "THREAD"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500"),
                    @HystrixProperty(name = "execution.timeout.enabled",value = "true"),
                   // @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout",value = "true"),
                    @HystrixProperty(name = "execution.timeout.enabled",value = "true"),
                    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
                    // 一个rolling window内最小的请求数。如果设为20，那么当一个rolling window的时间内（比如说1个rolling window是10秒）
                    // 收到19个请求，即使19个请求都失败，也不会触发circuit break。默认20
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "6"),
                    //触发短路的时间值，当该值设为5000时，则当触发circuit break后的5000毫秒内都会拒绝request，也就是5000毫秒后才会关闭circuit。默认5000
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000"),
                    //错误比率阀值，如果错误率>=该值，circuit会被打开，并短路所有请求触发fallback。默认50
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "20"),
                    //时间窗口值
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "5000"),
            },
            fallbackMethod = "getUsernameFallback")
    public String getUsername(String hello){
        String s= restTemplate.getForObject("http://hystrix-producer/user/getName", String.class);
        return s;
    }



    public String getUsernameFallback(String name){
        return "getUsernameFallback....";
    }

}
