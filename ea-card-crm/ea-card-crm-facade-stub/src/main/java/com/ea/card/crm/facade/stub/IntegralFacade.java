package com.ea.card.crm.facade.stub;

import com.ea.card.crm.facade.request.IntegralRequest;
import com.ea.card.crm.facade.response.GetIntegralResult;
import com.ea.card.crm.facade.response.IntegralDetailsResult;
import com.ea.card.crm.model.IntegralSet;
import com.lmtech.common.StateResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "ea-card-crm-ywop")
@RequestMapping(value = "/integral")
public interface IntegralFacade {
    /**
     * 积分获取接口
     *  openId 用户openId
     *  integralSource 积分来源 1、赠卡  2、签到 3、分享, 4、抽奖 5、兑换商品
     */
    @RequestMapping(value = "/getIntegral", method = RequestMethod.GET)
    @ResponseBody
    GetIntegralResult getIntegral(@RequestParam("openId") String openId);

    /**
     * 积分签到接口
     *  openId 用户openId
     *  integralType 积分操作类型 1、增加  2、消费
     *  integralSource 积分来源 1、赠卡  2、签到 3、分享, 4、抽奖 5、兑换商品
     *  integralOrderId 订单号Id
     *  integralNumber 积分值
     */
    @RequestMapping(value = "/integralSign", method = RequestMethod.POST)
    @ResponseBody
    StateResult integralSign(@RequestBody IntegralRequest integralRequest);

    /**
     * 积分明细接口
     *  openId 用户openId
     * integralType 积分操作类型 1、增加  2、消费
     * pageNum 第几页 默认 1
     * pageSize 每页条数 默认20
     */
    @RequestMapping(value = "/integralDetails", method = RequestMethod.POST)
    @ResponseBody
    IntegralDetailsResult integralDetails(@RequestBody IntegralRequest integralRequest);

    /**
     * 获取相应积分类型的配置信息
     *  integralSource 积分来源 1、赠卡  2、签到 3、分享, 4、抽奖 5、兑换商品
     */
    @RequestMapping(value = "/getIntegralSetAll", method = RequestMethod.GET)
    @ResponseBody
    StateResult getIntegralSetAll(@RequestParam("integralSource") int integralSource);

    /**
     * 获取积分抽奖页面数据
     * @param openId
     * @return
     */
    @RequestMapping(value = "/getIntegralLotteryData", method = RequestMethod.GET)
    StateResult getIntegralLotteryData(@RequestParam("openId") String openId);

    /**
     * 积分抽奖接口
     * @param openId
     * @return
     */
    @RequestMapping(value = "/integralLottery", method = RequestMethod.GET)
    StateResult integralLottery(@RequestParam("openId") String openId);

    /**
     * 每日任务列表接口
     */
    @RequestMapping(value = "/getAllTask", method = RequestMethod.GET)
    @ResponseBody
    StateResult getAllTask(@RequestParam("openId") String openId);

    /**
     * 分享任务成功回调接口
     */
    @RequestMapping(value = "/getShareTask", method = RequestMethod.GET)
    @ResponseBody
    StateResult getShareTask(@RequestParam("openId") String openId);

    /**
     * 签到任务成功回调接口
     */
    @RequestMapping(value = "/getSignTask", method = RequestMethod.GET)
    @ResponseBody
    StateResult getSignTask(@RequestParam("openId") String openId);
}
