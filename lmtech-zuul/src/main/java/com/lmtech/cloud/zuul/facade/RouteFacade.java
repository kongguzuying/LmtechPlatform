package com.lmtech.cloud.zuul.facade;

import com.lmtech.cloud.zuul.route.RefreshRouteService;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.response.BooleanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/route")
public class RouteFacade {

    @Autowired
    private RefreshRouteService refreshRouteService;

    @RequestMapping(value = "/refreshRoute", method = RequestMethod.POST)
    public BooleanResponse refreshRoute(NormalRequest request) {
        refreshRouteService.refreshRoute();

        BooleanResponse response = new BooleanResponse();
        response.setSuccess(true);
        response.setMessage("刷新Zuul路由信息成功");
        return response;
    }
}
