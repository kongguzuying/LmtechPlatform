package com.lmtech.scheduler.facade.stub;

import com.lmtech.common.StateResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调度器服务入口
 * @author huang.jb
 */
@FeignClient(name = "lmtech-scheduler")
@RequestMapping(value = "/scheduler")
public interface SchedulerFacade {
    /**
     * 启动任务
     * @param jobCode
     * @return
     */
    @RequestMapping(value = "/startJob", method = RequestMethod.POST)
    StateResult startJob(@RequestParam("jobCode") String jobCode);

    /**
     * 停止任务
     * @param jobCode
     * @return
     */
    @RequestMapping(value = "/stopJob", method = RequestMethod.POST)
    StateResult stopJob(@RequestParam("jobCode") String jobCode);
}
