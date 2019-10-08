package com.jufeng.cloud.sleuth.example004;

import brave.http.HttpAdapter;
import brave.http.HttpSampler;
import org.springframework.cloud.sleuth.instrument.web.ServerSampler;
import org.springframework.cloud.sleuth.instrument.web.SkipPatternProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-10-08 17:02
 **/
@Configuration
public class ConfigServerSampler {
    @Bean(name = ServerSampler.NAME)
    HttpSampler myHttpSampler(SkipPatternProvider provider) {
        Pattern pattern = provider.skipPattern(); //todo ： 你可以实现你自己的SkipPatternProvider
        return new HttpSampler() {

            @Override public <Req> Boolean trySample(HttpAdapter<Req, ?> adapter, Req request) {
                String url = adapter.path(request);
                System.out.println("ConfigServerSampler==========> >>>> " +url);
                boolean shouldSkip = pattern.matcher(url).matches();
                if (shouldSkip) { // 返回false 就是 跳过，这里可以自定定义什么请求是否进行跟踪请求
                    return false;
                }
                return null;
            }
        };
    }
}
