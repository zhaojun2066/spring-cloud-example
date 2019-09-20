package com.jufeng.cloud.hystrix.example008;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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




    @HystrixCommand(
            fallbackMethod = "err"
          //m   ignoreExceptions =ArithmeticException.class // 忽略该异常，如果忽略该异常，那么该异常会直接返回给client，不会走fallback
    )
    public List<User> getUserByNames(List<String> names){
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


    // 注意参数和返回值必须和 上面一致, 但是可以加一个Throwable throwable 参数用于打印异常具体信息
    public List<User> err(List<String> names,Throwable throwable){
        User u = new User();
        u.setName("error..." +throwable.getMessage());
        return Arrays.asList(u) ;
    }




}
