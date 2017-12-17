package com.ea.card.crm.facade;

import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.facade.request.IntegralRequest;
import com.ea.card.crm.facade.response.GetIntegralData;
import com.ea.card.crm.facade.response.GetIntegralResult;
import com.ea.card.crm.facade.response.IntegralDetailsData;
import com.ea.card.crm.facade.response.IntegralDetailsResult;
import com.ea.card.crm.facade.stub.IntegralFacade;
import com.ea.card.crm.model.*;
import com.ea.card.crm.service.IntegralLotteryService;
import com.ea.card.crm.service.IntegralService;
import com.ea.card.crm.service.LotteryProductService;
import com.ea.card.crm.service.MemberRegisterService;
import com.ea.card.crm.service.exception.NoneRegisterException;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.util.IdWorkerUtil;
import com.lmtech.util.LoggerManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ea.card.crm.constants.IntegralConstants;

import java.util.*;

@Api(description = "会员积分接口")
@RestController
@RequestMapping(value = "/integral")
public class IntegralFacadeImpl implements IntegralFacade {

    @Autowired
    private IntegralService integralService;
    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private IntegralLotteryService integralLotteryService;
    @Autowired
    private LotteryProductService lotteryProductService;

    /**
     * 积分获取接口
     */
    @Override
    @ApiOperation(value = "积分获取接口")
    @RequestMapping(value = "/getIntegral", method = RequestMethod.GET)
    @ResponseBody
    public GetIntegralResult getIntegral(@RequestParam("openId") String openId) {
        LoggerManager.info("积分获取接口被调用");
        String t_id = IdWorkerUtil.generateStringId();
        String userId = null;
        MemberRegister register = null;
        try {
            register = memberRegisterService.getByOpenId(openId);
            if (register == null) {
                throw new IllegalArgumentException(IntegralConstants.NO_REGIST);
            }
            userId = register.getUserId();
            GetIntegralResult response = integralService.getIntegral(userId);
            if (response.isSuccess()) {
                LoggerManager.info("积分获取接口被调用成功");
            }
            return response;
        } catch (Exception e) {
            LoggerManager.error("积分获取接口被调用失败");
            LoggerManager.error(e);
            GetIntegralResult errResponse = new GetIntegralResult();

            GetIntegralData getIntegralData = new GetIntegralData();
            getIntegralData.setIntegralMemberLevel(register.getmLevel());//会员等级 1、普通 2、尊享
            getIntegralData.setSumIntegralNumber(IntegralConstants.ZERO);
            getIntegralData.setUserId(userId);

            GetIntegralData new_getIntegralData = integralService.newGetIntegralData(userId, getIntegralData);

            errResponse.setData(new_getIntegralData);
            errResponse.settId(t_id);
            errResponse.setState(IntegralConstants._ONE);
            errResponse.setMsg(IntegralConstants.ERROR1);
            return errResponse;
        }
    }

    /**
     * 积分签到接口
     */
    @Override
    @ApiOperation(value = "积分签到接口")
    @RequestMapping(value = "/integralSign", method = RequestMethod.POST)
    @ResponseBody
    public StateResult integralSign(@RequestBody IntegralRequest integralRequest) {
        LoggerManager.info("积分签到接口被调用");
        String t_id = IdWorkerUtil.generateStringId();
        try {
            MemberRegister register = memberRegisterService.getByOpenId(integralRequest.getOpenId());
            if (register == null) {
                throw new IllegalArgumentException(IntegralConstants.NO_REGIST);
            }
            IntegralDO integralDO = new IntegralDO();
            integralDO.settId(t_id);
            integralDO.setUserId(register.getUserId());
            integralDO.setIntegralSource(integralRequest.getIntegralSource());
            integralDO.setIntegralOrderId(integralRequest.getIntegralOrderId());
            integralDO.setIntegralType(integralRequest.getIntegralType());
            integralDO.setUpdateTime(new Date());
            StateResult response = integralService.integralSign(integralDO);
            if (response.isSuccess()) {
                LoggerManager.info("积分签到接口被调用成功");
                integralService.noticeIntegralUpdate(register.getUserId());//通知微信服务器更新积分
            }
            response.settId(t_id);
            return response;
        } catch (Exception e) {
            LoggerManager.error("积分签到接口被调用失败");
            LoggerManager.error(e);
            StateResult errResponse = new StateResult();
            errResponse.setState(IntegralConstants._ONE);
            errResponse.settId(t_id);
            errResponse.setMsg(IntegralConstants.ERROR1);
            return errResponse;
        }
    }

    /**
     * 积分明细接口
     */
    @Override
    @ApiOperation(value = "积分明细接口")
    @RequestMapping(value = "/integralDetails", method = RequestMethod.POST)
    @ResponseBody
    public IntegralDetailsResult integralDetails(@RequestBody IntegralRequest integralRequest) {
        LoggerManager.info("积分明细接口被调用");
        MemberRegister register;
        String userId;
        String t_id = IdWorkerUtil.generateStringId();
        try {
            register = memberRegisterService.getByOpenId(integralRequest.getOpenId());
            if (register == null) {
                throw new IllegalArgumentException(IntegralConstants.NO_REGIST);
            }

            userId = register.getUserId();

            if (integralRequest.getPageNum() == IntegralConstants.ZERO ||
                    StringUtils.isBlank(String.valueOf(integralRequest.getPageNum()))) {
                integralRequest.setPageNum(IntegralConstants.ONE);
            }
            if (integralRequest.getPageSize() == IntegralConstants.ZERO ||
                    StringUtils.isBlank(String.valueOf(integralRequest.getPageSize()))) {
                integralRequest.setPageSize(IntegralConstants.TWENTY);
            }

            IntegralDetailsResult response = integralService.integralDetails(userId, integralRequest.getIntegralType(),
                    integralRequest.getPageNum(), integralRequest.getPageSize(), t_id);
            if (response.isSuccess()) {
                LoggerManager.info("积分明细接口被调用成功");
            }
            if (response.getData().getList() == null) {
                LoggerManager.info("积分明细接口被调用成功，积分明细数据为空");
                response.setData(errObject());
            }
            response.settId(t_id);
            return response;
        } catch (Exception e) {
            LoggerManager.error("积分明细接口被调用失败");
            LoggerManager.error(e);
            IntegralDetailsResult errResponse = new IntegralDetailsResult();
            errResponse.setData(errObject());
            errResponse.setState(IntegralConstants._ONE);
            errResponse.setMsg(IntegralConstants.ERROR1);
            return errResponse;
        }
    }

    public IntegralDetailsData errObject() {
        LoggerManager.info("生成错误对象数据被调用");
        IntegralDetailsData integralDetailsData = new IntegralDetailsData();
        integralDetailsData.setLastPage(IntegralConstants.ONE);
        integralDetailsData.setTotalPage(IntegralConstants.ZERO);
        integralDetailsData.setList(null);
        return integralDetailsData;
    }

    /**
     * 获取相应积分类型的配置信息
     */
    @Override
    @ApiOperation(value = "获取相应积分类型的配置信息接口")
    @RequestMapping(value = "/getIntegralSetAll", method = RequestMethod.GET)
    @ResponseBody
    public StateResult getIntegralSetAll(@RequestParam("integralSource") int integralSource) {
        LoggerManager.info("获取签到天数与积分接口被调用");
        String t_id = IdWorkerUtil.generateStringId();
        try {
            List<IntegralSet> list = integralService.getIntegralSetAll(integralSource);

            StateResult response = new StateResult();
            if (response.isSuccess()) {
                LoggerManager.info("获取签到天数与积分接口被调用成功");
            }
            response.setState(IntegralConstants.ZERO);
            response.settId(t_id);
            response.setData(list);
            return response;
        } catch (Exception e) {
            LoggerManager.error("获取签到天数与积分接口被调用失败");
            LoggerManager.error(e);
            return this.errObj(t_id);
        }
    }

    @ApiOperation(value = "获取积分抽奖页面数据接口")
    @Override
    public StateResult getIntegralLotteryData(@RequestParam("openId") String openId) {
        LoggerManager.info("获取积分抽奖页面数据接口被调用");
        ExeResult result = new ExeResult();
        try {
            MemberRegister register = memberRegisterService.getByOpenId(openId);
            if (register != null) {
                List<LotteryProduct> products = lotteryProductService.getProducts();
                List<IntegralLottery> prizes = integralLotteryService.getLatestPrizeList();
                int integralPay = integralLotteryService.getIntegralPay();

                Map<String, Object> data = new HashMap<>();
                data.put("products", products);
                data.put("prizes", prizes);
                data.put("integralPay", integralPay);
                result.setSuccess(IntegralConstants.TRUE);
                result.setData(data);
            } else {
                throw new NoneRegisterException();
            }
        } catch (ErrorCodeException e) {
            LoggerManager.error("获取积分抽奖页面数据接口被调用失败");
            result.setSuccess(IntegralConstants.FALSE);
            result.setMessage(e.getMessage());
            result.setErrCode(e.getErrorCode());
        } catch (Exception e) {
            LoggerManager.error("获取积分抽奖页面数据接口被调用失败");
            LoggerManager.error(e);
            result.setSuccess(IntegralConstants.FALSE);
            result.setErrCode(ErrorConstants.ERR_UNKNOW);
            result.setMessage(ErrorConstants.ERR_UNKNOW_MSG);
        }
        return result.getResult();
    }

    @ApiOperation(value = "积分抽奖接口")
    @Override
    public StateResult integralLottery(@RequestParam("openId") String openId) {
        LoggerManager.info("积分抽奖接口被调用");
        ExeResult result = new ExeResult();
        try {
            MemberRegister register = memberRegisterService.getByOpenId(openId);
            if (register != null) {
                Map<String, Object> data = integralService.integralLottery(register.getUserId());
                result.setSuccess(IntegralConstants.TRUE);
                result.setData(data);
            } else {
                throw new NoneRegisterException();
            }
        } catch (ErrorCodeException e) {
            LoggerManager.error("积分抽奖接口被调用失败");
            result.setSuccess(IntegralConstants.FALSE);
            result.setMessage(e.getMessage());
            result.setErrCode(e.getErrorCode());
        } catch (Exception e) {
            LoggerManager.error("积分抽奖接口被调用失败");
            LoggerManager.error(e);
            result.setSuccess(IntegralConstants.FALSE);
            result.setErrCode(ErrorConstants.ERR_UNKNOW);
            result.setMessage(ErrorConstants.ERR_UNKNOW_MSG);
        }
        return result.getResult();
    }

    public StateResult errObj(String t_id) {
        StateResult errResponse = new StateResult();
        errResponse.setState(IntegralConstants._ONE);
        errResponse.settId(t_id);
        errResponse.setMsg(IntegralConstants.ERROR1);
        errResponse.setData(null);
        return errResponse;
    }


    public IntegralSet getSignTask(int bonusType, String userId) {
        Map<String, Object> logMap = integralService.getIntegralSignLog(userId, IntegralConstants.SOURCE_TWO);

        Map<String, Integer> returnMap = integralService.getTodayData(logMap);
        int modDay = returnMap.get("modDay");
        if (modDay > IntegralConstants.ZERO) {
            modDay = modDay - IntegralConstants.ONE;
        } else {
            modDay = IntegralConstants.SEVEN - IntegralConstants.ONE;
        }
        IntegralSet iSet = integralService.getIntegralSetAll(bonusType).get(modDay);
        int isSign = IntegralConstants.ZERO;
        int dayCount = IntegralConstants.ZERO;
        int signCount = IntegralConstants.ZERO;
        if (logMap != null) {
            isSign = (int) logMap.get("isSign");
            dayCount = (int) logMap.get("dayCount");
            signCount = (int) logMap.get("signCount");
        }
        iSet.setIsSign(isSign);
        iSet.setDayCount(dayCount);
        iSet.setSignCount(signCount);
        return iSet;
    }

    public IntegralSet getShareTask(int bonusType, String userId) {
        Map<String, Object> logMap = integralService.getIntegralSignLog(userId, IntegralConstants.SOURCE_THERE);
        IntegralSet iSet = integralService.getIntegralSetAll(bonusType).get(IntegralConstants.ZERO);
        int isSign = IntegralConstants.ZERO;
        int dayCount = IntegralConstants.ZERO;
        if (logMap != null) {
            isSign = (int) logMap.get("isSign");
            dayCount = (int) logMap.get("dayCount");
        }
        iSet.setIsSign(isSign);
        iSet.setDayCount(dayCount);
        return iSet;
    }

    public IntegralSet getSendCardTask(int bonusType, String userId) {
        Map<String, Object> logMap = integralService.getIntegralSignLog(userId, IntegralConstants.SOURCE_ONE);
        IntegralSet iSet = integralService.getIntegralSetAll(bonusType).get(IntegralConstants.ZERO);
        int isSign = IntegralConstants.ZERO;
        int dayCount = IntegralConstants.ZERO;
        if (logMap != null) {
            isSign = (int) logMap.get("isSign");
            dayCount = (int) logMap.get("dayCount");
        }
        iSet.setIsSign(isSign);
        iSet.setDayCount(dayCount);
        return iSet;
    }

    /**
     * 每日任务列表接口
     */
    @Override
    @ApiOperation(value = "每日任务列表接口")
    @RequestMapping(value = "/getAllTask", method = RequestMethod.GET)
    @ResponseBody
    public StateResult getAllTask(@RequestParam("openId") String openId) {
        LoggerManager.info("每日任务列表接口被调用");
        String t_id = IdWorkerUtil.generateStringId();
        try {
            MemberRegister register = memberRegisterService.getByOpenId(openId);
            if (register == null) {
                LoggerManager.error(String.format("传入openid的关联用户还未注册,openId == %s", openId));
                throw new IllegalArgumentException(String.format("传入openid的关联用户还未注册,openId == %s", openId));
            }
            String userId = register.getUserId();
            List<BonusType> type = integralService.groupbytype();
            List<IntegralSet> list = new ArrayList<IntegralSet>();

            for (int i = 0; i < type.size(); i++) {
                if (type.get(i).getBonusType() == IntegralConstants.SOURCE_TWO) {//签到
                    list.add(this.getSignTask(IntegralConstants.SOURCE_TWO, userId));
                } else if (type.get(i).getBonusType() == IntegralConstants.SOURCE_ONE) {//赠卡
                    list.add(this.getSendCardTask(IntegralConstants.SOURCE_ONE, userId));
                } else {//分享
                    list.add(this.getShareTask(IntegralConstants.SOURCE_THERE, userId));
                }
            }

            StateResult response = new StateResult();
            response.setState(IntegralConstants.ZERO);
            response.settId(t_id);
            response.setData(list);
            LoggerManager.info("每日任务列表接口被调用成功");
            return response;
        } catch (Exception e) {
            LoggerManager.error("每日任务列表接口被调用失败");
            LoggerManager.error(e);
            return this.errObj(t_id);
        }
    }

    /**
     * 分享任务成功回调接口
     */
    @Override
    @ApiOperation(value = "分享任务成功回调接口")
    @RequestMapping(value = "/getShareTask", method = RequestMethod.GET)
    @ResponseBody
    public StateResult getShareTask(@RequestParam("openId") String openId) {
        LoggerManager.info("分享任务成功回调接口");
        String t_id = IdWorkerUtil.generateStringId();
        try {
            MemberRegister register = memberRegisterService.getByOpenId(openId);
            if (register == null) {
                LoggerManager.error(String.format("传入openid的关联用户还未注册,openId == %s", openId));
                throw new IllegalArgumentException(String.format("传入openid的关联用户还未注册,openId == %s", openId));
            }
            String userId = register.getUserId();
            StateResult response = new StateResult();
            response.setState(IntegralConstants.ZERO);
            response.settId(t_id);
            response.setData(this.getShareTask(IntegralConstants.SOURCE_THERE, userId));
            LoggerManager.info("分享任务成功回调接口被调用成功");
            return response;
        } catch (Exception e) {
            LoggerManager.error("分享任务成功回调接口被调用失败");
            LoggerManager.error(e);
            return this.errObj(t_id);
        }
    }

    /**
     * 签到任务成功回调接口
     */
    @Override
    @ApiOperation(value = "签到任务成功回调接口")
    @RequestMapping(value = "/getSignTask", method = RequestMethod.GET)
    @ResponseBody
    public StateResult getSignTask(@RequestParam("openId") String openId) {
        LoggerManager.info("签到任务成功回调接口被调用");
        String t_id = IdWorkerUtil.generateStringId();
        try {
            MemberRegister register = memberRegisterService.getByOpenId(openId);
            if (register == null) {
                LoggerManager.error(String.format("传入openid的关联用户还未注册,openId == %s", openId));
                throw new IllegalArgumentException(String.format("传入openid的关联用户还未注册,openId == %s", openId));
            }
            String userId = register.getUserId();
            StateResult response = new StateResult();
            response.setState(IntegralConstants.ZERO);
            response.settId(t_id);
            response.setData(this.getSignTask(IntegralConstants.SOURCE_TWO, userId));
            LoggerManager.info("签到任务成功回调接口被调用成功");
            return response;
        } catch (Exception e) {
            LoggerManager.error("签到任务成功回调接口被调用失败");
            LoggerManager.error(e);
            return this.errObj(t_id);
        }
    }
}
