package com.jufeng.cloud.sleuth.example004;

import org.springframework.cloud.sleuth.annotation.ContinueSpan;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-29 16:47
 **/

@Component
public class OtherService {



    @NewSpan("getAge")
    @ContinueSpan(log = "hello")
    public int getAge(){
        return 10;
    }

    @NewSpan("getName")
    @ContinueSpan(log = "k-name")
    public String getName(@SpanTag(key = "name",expression = "'hello' + ' word'" ) String name){
        return "jufeng";
    }


    @NewSpan("getUser")
    @ContinueSpan(log = "j-user")
    public String getUser(@SpanTag(key = "name", resolver = UserTagValueResolver.class) String name){
        return "jufeng-gege";
    }
}
