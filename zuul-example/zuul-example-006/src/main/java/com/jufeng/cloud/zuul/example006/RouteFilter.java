package com.jufeng.cloud.zuul.example006;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER;

/**
 * @program: spring-cloud-example
 * @description: setRouteHost()
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-23 15:47
 **/
@Slf4j
@Component
public class RouteFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SIMPLE_HOST_ROUTING_FILTER_ORDER  -1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext(); // 可以通过RequestContext 进行判断
        return true; // 需要进行过滤的条件，这里直接返回true
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        System.out.println(
                "routing filter"
        );
        URI uri= null;
        try {
           /* if (!targetUrl.startsWith("http://") && !targetUrl.startsWith("https://")){
                targetUrl = "http://" + targetUrl;
            }*/
           //这里是动态改变，你可以从cache 中拿出来
            uri = new URI("http://localhost:3000");
            ctx.setRouteHost(uri.toURL());
        } catch (URISyntaxException e) {
            log.error("router uri URISyntaxException {}", e.getMessage());
            //  e.printStackTrace();
        } catch (MalformedURLException e) {
            log.error("router uri MalformedURLException {}", e.getMessage());
            //  e.printStackTrace();
        }
        return null;
    }
}
