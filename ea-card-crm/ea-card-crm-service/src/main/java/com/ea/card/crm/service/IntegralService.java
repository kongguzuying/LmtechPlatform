package com.ea.card.crm.service;

import com.ea.card.crm.facade.response.GetIntegralData;
import com.ea.card.crm.facade.response.GetIntegralResult;
import com.ea.card.crm.facade.response.IntegralDetailsResult;
import com.ea.card.crm.model.BonusType;
import com.ea.card.crm.model.IntegralDO;
import com.ea.card.crm.model.IntegralSet;
import com.ea.card.crm.model.IntegralSignLog;
import com.ea.card.crm.service.vo.WechatMessageResponse;
import com.lmtech.common.StateResult;

import java.util.List;
import java.util.Map;

/**
 * 积分服务
 */
public interface IntegralService {
    /**
     * 积分获取接口
     */
    GetIntegralResult getIntegral(String userId);

    /**
     * 获取历史积分记录
     * @param userId
     * @return
     */
    GetIntegralResult getHistoryIntegral(String userId);

    /**
     * 积分签到接口
     */
    StateResult integralSign(IntegralDO integralDO);

    /**
     * 积分明细接口
     * @param userId 用户ID
     * @param integralType 积分操作类型 1、增加  2、消费
     * @param pageNum 第几页 默认 1
     * @param pageSize 每页条数 默认20
     * @param t_id 流水号ID
     */
    IntegralDetailsResult integralDetails(String userId, int integralType, int pageNum,
                                          int pageSize, String t_id);

    /**
     * 获取连续签到天数和当日是否已签到
     * @param userId
     * @return
     */
    Map<String,Object> getIntegralSignLog(String userId,int integralSource);

    /**
     * 获取对应某天的积分值
     * @param dayNo
     * @return
     */
    Integer getIntegralSet(int dayNo,int integralSource);

    /**
     * 获取所有的积分配置值
     * @return
     */
    List<IntegralSet> getIntegralSetAll(int integralSource);

    /**
     * 积分更新通知
     * @param userId
     */
    void noticeIntegralUpdate(String userId);

    /**
     * 增加积分
     * @param userId
     * @param integral
     * @param source 积分来源 1、赠卡  2、签到 3、分享,
     */
    void increaseIntegral(String userId, long integral, int source);

    /**
     * 消费积分
     * @param userId
     * @param integral
     * @param source 积分来源 4、抽奖 5、兑换商品
     */
    void consumeIntegral(String userId, long integral, int source);

    Map<String, Object> integralLottery(String userId);

    GetIntegralData newGetIntegralData(String userId,GetIntegralData old_getIntegralData);

    List<BonusType> groupbytype();

    void existOfUserId(String userId,int integralSource);

    List<IntegralSignLog> getIntegralSignLogList(String userId,int integralSource);

    Map<String, Integer> getTodayData(Map<String, Object> rmap);
    
    /**
     * 微信公众号推送消息
     * @param openId
     */
    void sendIntegralMsgToWx(String openId, String msgContent);
    
    /**
     * 更新微信公众号access_token
     * @return
     */
    WechatMessageResponse updateToken();

    /**
     * 赠卡次数回写接口
     * @param userId
     * @return
     */
    boolean sendCardCallBack(String userId);
}
