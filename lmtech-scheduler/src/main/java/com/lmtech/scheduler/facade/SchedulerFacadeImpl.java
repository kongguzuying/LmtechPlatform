package com.lmtech.scheduler.facade;

import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.scheduler.engine.SchedulerEngine;
import com.lmtech.scheduler.facade.stub.SchedulerFacade;
import com.lmtech.util.LoggerManager;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "调度器接口")
@RestController
@RequestMapping(value = "/scheduler")
public class SchedulerFacadeImpl implements SchedulerFacade {

    @Autowired
    private SchedulerEngine schedulerEngine;

    @Override
    public StateResult startJob(@RequestParam String jobCode) {
        ExeResult result = new ExeResult();
        try {
            schedulerEngine.start(jobCode);

            result.setSuccess(true);
            result.setMessage("启动成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("系统繁忙");
            LoggerManager.error(e);
        }
        return result.getResult();
    }

    @Override
    public StateResult stopJob(@RequestParam String jobCode) {
        ExeResult result = new ExeResult();
        try {
            schedulerEngine.stop(jobCode);

            result.setSuccess(true);
            result.setMessage("停止成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("系统繁忙");
            LoggerManager.error(e);
        }
        return result.getResult();
    }
}
