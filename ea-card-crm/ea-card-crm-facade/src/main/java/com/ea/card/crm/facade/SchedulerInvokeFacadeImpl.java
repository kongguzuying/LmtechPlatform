package com.ea.card.crm.facade;

import com.ea.card.crm.dao.IntegralSignLogDao;
import com.ea.card.crm.facade.stub.SchedulerInvokeFacade;
import com.ea.card.crm.service.CardPresentRecordService;
import com.ea.card.crm.service.MemberRegisterService;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.util.LoggerManager;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Api(description = "定时任务接口", hidden = true)
@RestController
@RequestMapping(value = "/scheinvoke")
public class SchedulerInvokeFacadeImpl implements SchedulerInvokeFacade {

    @Autowired
    private IntegralSignLogDao integralSignLogDao;
    @Autowired
    private CardPresentRecordService cardPresentRecordService;
    @Autowired
    private MemberRegisterService memberRegisterService;

    /**
     * 定时重置每天的是否签到和当日签到次数为0
     *
     * @return
     */
    public StateResult updateIsSignLog() {
        ExeResult result = new ExeResult();
        LoggerManager.info("SchedulerInvokeFacadeImpl-->>updateLogByScheduled被调用");
        boolean success = integralSignLogDao.updateIsSignLog(new Date()) > 0;
        result.setSuccess(success);
        return result.getResult();
    }

    /**
     * 判断是否连续签到，不是重置次数为0
     * 该任务要先执行
     *
     * @return
     */
    public StateResult updateSignCountLog() {
        ExeResult result = new ExeResult();
        LoggerManager.info("SchedulerInvokeFacadeImpl-->>updateSignCountLog被调用");
        boolean success = integralSignLogDao.updateSignCountLog(new Date()) > 0;
        result.setSuccess(success);
        return result.getResult();
    }

    /**
     * 刷新赠送卡片超时
     * @return
     */
    public StateResult refreshPresentCardOvertime() {
        ExeResult result = new ExeResult();
        LoggerManager.info("SchedulerInvokeFacadeImpl-->>refreshPresentCardOvertime");
        cardPresentRecordService.setPresentOvertime();
        result.setSuccess(true);
        return result.getResult();
    }

    @Override
    public StateResult refreshTrailOvertime() {
        ExeResult result = new ExeResult();
        LoggerManager.info("SchedulerInvokeFacadeImpl-->>refreshTrailVpass");
        memberRegisterService.setTrailOvertime();
        result.setSuccess(true);
        return result.getResult();
    }

}
