package com.lmtech.infrastructure.service.impl;

import com.lmtech.common.RouteConfig;
import com.lmtech.dao.Dao;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.infrastructure.dao.TenancyDao;
import com.lmtech.infrastructure.exceptions.TenancyNotExistException;
import com.lmtech.infrastructure.model.Tenancy;
import com.lmtech.infrastructure.service.TenancyService;
import com.lmtech.redis.service.RedisDataService;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TenancyServiceImpl extends AbstractDbManagerBaseImpl<Tenancy> implements TenancyService {

    private final String REFRESH_ROUTE_URL = "http://lmtech-zuul/route/refreshRoute";
    private final String REDIS_TABLE_NAME = "tenancy_routes";
    private Map<String, String> dynamicServiceMap = new HashMap<>();

    @Autowired
    private TenancyDao tenancyDao;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisDataService redisDataService;

    public TenancyServiceImpl() {
        //TODO 放到配置中
        dynamicServiceMap.put("ea-card-crm", "/crm/**");
        dynamicServiceMap.put("ea-card-crm-admin", "/crmadmin/**");
    }

    @Override
    public Dao getDao() {
        return tenancyDao;
    }

    @Override
    public Tenancy getByCode(String code) {
        if (StringUtil.isNullOrEmpty(code)) {
            throw new IllegalArgumentException("传入code不允许为空");
        }

        return tenancyDao.getByCode(code);
    }

    @Override
    public void activeTenancy(String code) {
        if (StringUtil.isNullOrEmpty(code)) {
            throw new IllegalArgumentException("传入code不允许为空");
        }

        Tenancy tenancy = getByCode(code);
        if (tenancy == null) {
            throw new TenancyNotExistException();
        }

        //刷新路由表
        refreshRouteToCache();
        //通知网关
        NormalResponse response = restTemplate.postForObject(REFRESH_ROUTE_URL, null, NormalResponse.class);
        if (response.isSuccess()) {
            tenancy.setStatus(Tenancy.STATUS_ACTIVE);
            tenancyDao.updateById(tenancy);
        } else {
            throw new RuntimeException("通知网关更新路由表失败");
        }
    }

    @Override
    public void disableTenancy(String code) {
        if (StringUtil.isNullOrEmpty(code)) {
            throw new IllegalArgumentException("传入code不允许为空");
        }

        Tenancy tenancy = getByCode(code);
        if (tenancy == null) {
            throw new TenancyNotExistException();
        }

        //刷新路由表
        refreshRouteToCache();
        //通知网关
        NormalResponse response = restTemplate.postForObject(REFRESH_ROUTE_URL, null, NormalResponse.class);
        if (response.isSuccess()) {
            tenancy.setStatus(Tenancy.STATUS_DISABLE);
            tenancyDao.updateById(tenancy);
        } else {
            throw new RuntimeException("通知网关更新路由表失败");
        }
    }

    private void refreshRouteToCache() {
        Tenancy param = new Tenancy();
        param.setDelete(false);
        List<Tenancy> tenancyList = tenancyDao.getDataList(param);

        if (!CollectionUtil.isNullOrEmpty(tenancyList)) {
            List<RouteConfig> configs = new ArrayList<>();
            for (Tenancy tenancy : tenancyList) {
                for (String serviceId : dynamicServiceMap.keySet()) {
                    String servicePath = dynamicServiceMap.get(serviceId);
                    RouteConfig routeConfig = new RouteConfig();
                    routeConfig.setId(serviceId + "-" + tenancy.getCode());
                    routeConfig.setApiName(tenancy.getCode());
                    routeConfig.setServiceId(serviceId + "-" + tenancy.getCode());
                    routeConfig.setPath("/" + tenancy.getCode() +servicePath);
                    routeConfig.setEnabled(true);
                    routeConfig.setRetryable(true);
                    configs.add(routeConfig);
                }
            }
            redisDataService.saveAll(REDIS_TABLE_NAME, configs);
        }
    }
}
