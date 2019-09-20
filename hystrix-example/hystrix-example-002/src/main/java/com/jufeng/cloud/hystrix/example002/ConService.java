package com.jufeng.cloud.hystrix.example002;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
            commandProperties = {
               @HystrixProperty(name = "execution.isolation.strategy",value = "THREAD"),
               @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "4000"),
               @HystrixProperty(name = "execution.timeout.enabled",value = "true"),
               @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout",value = "true"),
               @HystrixProperty(name = "execution.timeout.enabled",value = "true"),
            },
            threadPoolKey = "ConService",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize",value = "2"),
                    @HystrixProperty(name = "maxQueueSize",value = "30"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold",value = "100"),
            },
            fallbackMethod = "getUsernameFallback")
    public String getUsername( String name){
        String s= restTemplate.getForObject("http://hystrix-producer/user/getName", String.class);
        return s;
    }



    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.strategy",value = "THREAD"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "4000"),
                    @HystrixProperty(name = "execution.timeout.enabled",value = "true"),
                    @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout",value = "true"),
                    @HystrixProperty(name = "execution.timeout.enabled",value = "true"),
                    @HystrixProperty(name = "requestCache.enabled",value = "true"),
                    @HystrixProperty(name = "requestLog.enabled",value = "true")
            },
            threadPoolKey = "say",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize",value = "2"),
                    @HystrixProperty(name = "maxQueueSize",value = "30"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold",value = "100"),
            },
            fallbackMethod = "getUsernameFallback")
    public String say( String what){
        String s= restTemplate.getForObject("http://hystrix-producer//say/"+what, String.class);
        return s;
    }


    // 这里用于设置第一个fallback失败后，还可以再进行fallback ，终极fallback
    @HystrixCommand(fallbackMethod = "getUsernameLastFallBack")
    public String getUsernameFallback(String name){
        return "getUsernameFallback....";
    }

    public String getUsernameLastFallBack(String name){
        return "getUsernameLastFallBack....";
    }
}
