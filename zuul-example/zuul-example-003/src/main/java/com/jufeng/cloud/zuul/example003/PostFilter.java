package com.jufeng.cloud.zuul.example003;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-23 15:47
 **/

@Component
public class PostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER   -1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext(); // 可以通过RequestContext 进行判断
        return true; // 需要进行过滤的条件，这里直接返回true
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println(
                "post filter"
        );
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse servletResponse = context.getResponse();
        servletResponse.addHeader("X-Sample", UUID.randomUUID().toString());
        return null;
    }
}
