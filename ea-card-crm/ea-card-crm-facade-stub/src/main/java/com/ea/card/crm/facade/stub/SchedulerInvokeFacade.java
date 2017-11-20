package com.ea.card.crm.facade.stub;

import com.lmtech.common.StateResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm")
@RequestMapping(value = "/scheinvoke")
public interface SchedulerInvokeFacade {
    /**
     * 定时重置每天的是否签到和当日签到次数为0
     * @return
     */
    @RequestMapping(value = "/updateIsSignLog", method = RequestMethod.GET)
    StateResult updateIsSignLog();

    /**
     * 判断是否连续签到，不是重置次数为0
     * 该任务要先执行
     * @return
     */
    @RequestMapping(value = "/updateSignCountLog", method = RequestMethod.GET)
    StateResult updateSignCountLog();

    /**
     * 刷新赠送卡片超时
     * @return
     */
    @RequestMapping(value = "/refreshPresentCardOvertime", method = RequestMethod.GET)
    StateResult refreshPresentCardOvertime();

    /**
     * 刷新会员试用超时
     * @return
     */
    @RequestMapping(value = "/refreshTrailOvertime", method = RequestMethod.GET)
    StateResult refreshTrailOvertime();
}
