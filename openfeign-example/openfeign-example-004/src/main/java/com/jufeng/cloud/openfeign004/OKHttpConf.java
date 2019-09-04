package com.jufeng.cloud.openfeign004;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-04 14:58
 **/
@Slf4j
@Configuration
public class OKHttpConf {
    @Bean
    @ConditionalOnMissingBean(OkHttpClient.class)
    public OkHttpClient okHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client= builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
               log.info("=====================" + chain.request().url().pathSegments());
                Response response = chain.proceed(chain.request());
                return response;
            }
        })
                .retryOnConnectionFailure(true)
                .readTimeout(3000,TimeUnit.MINUTES)
                .connectionPool(new ConnectionPool(10,5L, TimeUnit.MINUTES))
                .build();
       return client;
    }
}
