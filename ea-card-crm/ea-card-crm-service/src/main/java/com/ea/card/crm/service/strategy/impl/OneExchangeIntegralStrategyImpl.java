package com.ea.card.crm.service.strategy.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ea.card.crm.service.exception.NoEnoughIntegralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.annotations.TableField;
import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.constants.IntegralConstants;
import com.ea.card.crm.dao.ExchangeIntegralRecordDao;
import com.ea.card.crm.dao.MemberRegisterDao;
import com.ea.card.crm.dao.WxActiveMessageDao;
import com.ea.card.crm.dao.impl.WxActiveMessageDaoImpl;
import com.ea.card.crm.facade.response.GetIntegralData;
import com.ea.card.crm.facade.response.GetIntegralResult;
import com.ea.card.crm.model.ExchangeIntegralRecord;
import com.ea.card.crm.model.IntegralRule;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.model.WxActiveMessage;
import com.ea.card.crm.service.IntegralRuleService;
import com.ea.card.crm.service.IntegralService;
import com.ea.card.crm.service.exception.IntegralConsumeException;
import com.ea.card.crm.service.strategy.ExchangeIntegralStrategy;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;

/**
 * 购买商品兑换积分记录 Strategy 实现类
 * 策略模式：一元兑换一积分
 * @author huacheng.li
 *
 */
@SuppressWarnings("serial")
@Service
public class OneExchangeIntegralStrategyImpl extends AbstractDbManagerBaseImpl<ExchangeIntegralRecord>
		implements ExchangeIntegralStrategy {
	
	@Autowired
	private ExchangeIntegralRecordDao exchangeIntegralRecordDao;
	
	@Autowired
	private MemberRegisterDao memberRegisterDao;
	
	@Autowired
	private WxActiveMessageDao wxActiveMessageDao;
	
	@Autowired
	private IntegralRuleService integralRuleService;
	
	@Autowired
	private IntegralService integralService;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public Dao getDao() {
		return exchangeIntegralRecordDao;
	}

	@Override
	public void exchangeIntegral(String userId, String tId, String orderSn, String productIds,
			long integralNum, int type, String shopName, String payChannel, String paytime, String sourceType) {
//		StrategyContext<ExchangeIntegralStrategy> strategyContext = new 
//				StrategyContext<ExchangeIntegralStrategy>(new OneExchangeIntegralStrategyImpl());
//		ExchangeIntegralStrategy exchangeIntegralStrategy = strategyContext.getStrategyContext();
		MemberRegister memberRegister = memberRegisterDao.getByUserId(userId);
		int level = memberRegister.getmLevel();
		//sourceType  1线上购买支付  2 扫码支付
		IntegralRule integralRule = integralRuleService.selectByType(sourceType, level);
		if(integralRule!=null) {
			integralNum = (long) Math.ceil(integralNum)*integralRule.getRule();
			LoggerManager.info("sourceType:"+sourceType+",使用积分规则1元"+integralRule.getRule()+"积分");
		}else {
			integralNum = (long) Math.ceil(integralNum);
			LoggerManager.info("对应积分规则未配置，使用默认积分规则1元1积分");
		}
		
		if(type == IntegralConstants.ZERO) {//增加积分
			integralService.increaseIntegral(userId, integralNum, IntegralConstants.THERE);
		}else if(type == IntegralConstants.ONE){//扣减积分
			//查询积分值
			GetIntegralResult integralResult = integralService.getIntegral(userId);
			GetIntegralData integralData= integralResult.getData();
			long integralNumber = integralData.getSumIntegralNumber();
			//积分不足
			if(integralNumber<=integralNum) {
				throw new NoEnoughIntegralException();
			}
			integralService.consumeIntegral(userId, integralNum, IntegralConstants.THERE);
		}else {
			throw new IntegralConsumeException(ErrorConstants.ERR_INTEGRAL_TYPE_ERROR_MSG,ErrorConstants.ERR_INTEGRAL_TYPE_ERROR);
		}
		//插入兑换积分记录流水
		ExchangeIntegralRecord exchangeIntegralRecord = new ExchangeIntegralRecord();
		
		exchangeIntegralRecord.setTid(tId);
		exchangeIntegralRecord.setUserId(userId);
		exchangeIntegralRecord.setOrderNo(orderSn);
		exchangeIntegralRecord.setProductIds(productIds);
		exchangeIntegralRecord.setExchangePoints(integralNum);
		exchangeIntegralRecord.setStatus(type);
		String id = addObjectEntity(exchangeIntegralRecord);
		if(StringUtil.isNullOrEmpty(id)) 
			throw new IntegralConsumeException(ErrorConstants.ERR_EXCHANGE_INTEGRAL_INSERT_ERROR_MSG,ErrorConstants.ERR_EXCHANGE_INTEGRAL_INSERT_ERROR);
		else {
			//TODO 推送微信通知
//			MemberRegister memberRegister = memberRegisterDao.getByUserId(userId);
			if(memberRegister!=null) {
				WxActiveMessage wxActiveMessage = wxActiveMessageDao.selectByCode(memberRegister.getCode());
				if(wxActiveMessage!=null) {
					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
					String tempalteTime = sdf.format(new Date());
					StringBuffer sb = new StringBuffer();
					sb.append("{'first':{'value':'"+tempalteTime+"'},");
					sb.append("'keyword1':{'value':'"+shopName+"'},");//消费门店
					sb.append("'keyword2':{'value':'"+integralNum+"'},");//消费金额
					sb.append("'keyword3':{'value':'"+payChannel+"'},");//支付方式
					sb.append("'keyword4':{'value':'"+integralNum+"'},");//赠送积分
					sb.append("'keyword5':{'value':'"+paytime+"'},");//消费时间
					sb.append("'remark':{'value':'点击查看积分奖励详情，第一时间了解积分奖励动态>>'}}");
					//TODO 待申请模块
					//String msgContent = sb.toString();
					//integralService.sendIntegralMsgToWx(wxActiveMessage.getFromUserName(),msgContent);
				}else {
					LoggerManager.debug("用户UserId:["+userId+"]code:["+memberRegister.getCode()+"],激活过星联卡数据异常,激活时未记录微信公众号openId");
				}
				
			}else {
				LoggerManager.debug("用户UserId:["+userId+"],未激活过星联卡");
			}
		}
	}
	
	

}
