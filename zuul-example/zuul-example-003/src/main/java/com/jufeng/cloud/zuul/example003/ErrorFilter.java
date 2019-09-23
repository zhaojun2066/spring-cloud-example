package com.jufeng.cloud.zuul.example003;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-23 15:47
 **/

@Component
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return  -1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().containsKey("throwable"); // 包含这个，就进入
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println(
                "error filter"
        );
        RequestContext ctx = RequestContext.getCurrentContext();
        Object e = ctx.get("throwable");
        HttpServletResponse response = ctx.getResponse();
        response.setContentType("application/json; charset=utf8");
        response.setStatus(HttpStatus.OK.value());
        if (e != null && e instanceof ZuulException) {
            //ZuulException zuulException = (ZuulException) e;
            //((ZuulException) e).printStackTrace();
            // Remove error code to prevent further error handling in follow up filters
            // 删除该异常信息,不然在下一个过滤器中还会被执行处理
            ctx.remove("throwable");
            // 根据具体的业务逻辑来处理
            ctx.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.print("hello error");
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }

        }
        return null;
    }
}
