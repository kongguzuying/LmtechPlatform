package com.lmtech.cloud.zuul.route;

import com.lmtech.common.RouteConfig;
import com.lmtech.redis.service.RedisDataService;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DynamicRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private final String REDIS_TABLE_NAME = "tenancy_routes";

    private Map<String, ZuulProperties.ZuulRoute> currRoutes;

    private ZuulProperties properties;
    private RedisDataService redisDataService;

    public DynamicRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }

    public void setRedisDataService(RedisDataService redisDataService) {
        this.redisDataService = redisDataService;
    }

    public Map<String, ZuulProperties.ZuulRoute> getCurrentRoutes() {
        return currRoutes;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<String, ZuulProperties.ZuulRoute>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从cache中加载路由信息
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

    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromCache(){
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
        List<RouteConfig> zullRotes = redisDataService.getAll(REDIS_TABLE_NAME, RouteConfig.class);
        for (RouteConfig result : zullRotes) {
            if(StringUtil.isNullOrEmpty(result.getPath()) && StringUtil.isNullOrEmpty(result.getUrl()) ){
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(zuulRoute, result);
            } catch (Exception e) {
                LoggerManager.error(e);
            }
            routes.put(zuulRoute.getPath(),zuulRoute);
        }
        currRoutes = routes;
        return routes;
    }


}
