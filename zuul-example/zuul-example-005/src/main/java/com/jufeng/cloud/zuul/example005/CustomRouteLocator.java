package com.jufeng.cloud.zuul.example005;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: spring-cloud-example
 * @description: 可以在你需要更新的地方调用 CustomRouteLocator.refresh()，就可以了
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-23 16:44
 **/
@Slf4j
@Component
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private ZuulProperties properties;

    public CustomRouteLocator(@Autowired DispatcherServletPath dispatcherServletPath, @Autowired ZuulProperties zuulProperties) {
        super(dispatcherServletPath.getPrefix(), zuulProperties);
        this.properties = zuulProperties;
        log.info("servletPath:{}", dispatcherServletPath.getPrefix());
    }


    /**
     * 父类会调用该方法
     * @return
     */
    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<String, ZuulProperties.ZuulRoute>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromCache());
        //优化一下配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    // 设置路由
    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromCache() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
        //这里你可以写成自己从数据库，或者其他mq 读取，现在我只是写死了
        ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
        zuulRoute.setRetryable(false);
        zuulRoute.setStripPrefix(false);
        zuulRoute.setPath("/user/hello");
        zuulRoute.setId("/user/hello");
        zuulRoute.setUrl("http://localhost:3000");
        routes.put(zuulRoute.getPath(), zuulRoute);
        return routes;
    }

    public void modifyRouteMap(Map<String, ZuulProperties.ZuulRoute> params) {
        Map<String, ZuulProperties.ZuulRoute> routeMap = getRoutesMap();
        routeMap.putAll(params);
    }

    @Override
    public void refresh() {
        //父类方法，里面会调用locateRoutes() ,也就是你自己实现的locateRoutes() 方法
        doRefresh();
    }
}
