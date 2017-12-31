package com.ea.card.trade.stub;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通知入口
 * @author huang.jb
 */
@FeignClient(name = "ea-card-trade")
@RequestMapping(value = "/notify")
public interface NotifyFacade {
    /**
     * 微信支付结果通知
     * @param request
     * @return
     */
    @RequestMapping(value = "/wxPayNotify")
    void wxPayNotify(HttpServletRequest request, HttpServletResponse response);
}
