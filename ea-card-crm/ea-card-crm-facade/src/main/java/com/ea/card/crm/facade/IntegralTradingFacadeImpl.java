package com.ea.card.crm.facade;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.constants.IntegralConstants;
import com.ea.card.crm.facade.request.IntegralTradingRequest;
import com.ea.card.crm.facade.request.ExchangeIntegralRequest;
import com.ea.card.crm.facade.request.IntegralProductListRequest;
import com.ea.card.crm.facade.stub.IntegralTradingFacade;
import com.ea.card.crm.model.IntegralTradingRecord;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.IntegralService;
import com.ea.card.crm.service.IntergralTradingService;
import com.ea.card.crm.service.MemberRegisterService;
import com.ea.card.crm.service.strategy.ExchangeIntegralStrategy;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.util.IdWorkerUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 积分交易
 * @author huacheng.li
 *
 */

@Api(description = "积分交易")
@RestController
@RequestMapping(value = "/integraltrading")
public class IntegralTradingFacadeImpl implements IntegralTradingFacade {
	
	@Autowired
	private IntergralTradingService intergralTradingService;
	
	@Autowired
	private ExchangeIntegralStrategy exchangeIntegralStrategy;
	
	@Autowired
	private IntegralService integralService;
	
	@Autowired
	private MemberRegisterService memberRegisterService;
	
	
	@ApiOperation(value = "积分兑换商品")
	@RequestMapping(value = "/exchangeProduct", method = RequestMethod.POST)
	@ResponseBody
	public StateResult exchangeProduct(@RequestBody IntegralTradingRequest request) {
		ExeResult result = new ExeResult();
//			if (StringUtil.isNullOrEmpty(request.getUserId())) {
//                throw new IllegalArgumentException("用户ID不能为空");
//            }
		if (StringUtil.isNullOrEmpty(request.getOpenId())) {
			throw new IllegalArgumentException("用户授权OpenId不能为空");
		}
		if (StringUtil.isNullOrEmpty(request.getConsumptionIntegral())) {
			throw new IllegalArgumentException("兑换商品所需积分不能为空");
		}
		if (StringUtil.isNullOrEmpty(request.getToken())) {
			throw new IllegalArgumentException("用户登录token不能为空");
		}
		if (StringUtil.isNullOrEmpty(request.getReceiverinfoId())) {
			throw new IllegalArgumentException("收货地址ID不能为空");
		}
		if(request.getGoodslist()==null||request.getGoodslist().size()==0) {
			throw new IllegalArgumentException("兑换商品不能为空");
		}
		if(StringUtil.isNullOrEmpty(request.getAmount())) {
			throw new IllegalArgumentException("商品数量不能为空");
		}
		//获取用户userId
		MemberRegister memberRegister = memberRegisterService.getByOpenId(request.getOpenId());
		if(memberRegister==null) {
			throw new IllegalArgumentException("用户信息不存在");
		}
		request.setUserId(memberRegister.getUserId());
		//生成流水号
		request.settId(IdWorkerUtil.generateStringId());
		IntegralTradingRecord recordResult= intergralTradingService.consumerProduct(request);
		result.setSuccess(true);
		result.setData(recordResult);

        return result.getResult();
	}
	
	@ApiOperation(value = "积分兑换商品列表")
	@RequestMapping(value = "/tradingProductList", method = RequestMethod.POST)
	public @ResponseBody StateResult exchangeProductList(@RequestBody IntegralProductListRequest request) throws ParseException {
		ExeResult result = new ExeResult();
		if(StringUtil.isNullOrEmpty(request.getToken())) {
			throw new IllegalArgumentException("用户token不能为空");
		}
		//获取用户userId
		MemberRegister memberRegister = memberRegisterService.getByOpenId(request.getOpenId());
		if(memberRegister==null) {
			throw new IllegalArgumentException("用户信息不存在");
		}
		LinkedHashMap<String, Object> list = intergralTradingService.exchangeProductList(request.getToken(), request.getPageNum(), request.getPageSize(), memberRegister.getUserId());
		result.setSuccess(true);
		result.setData(list);

		return result.getResult();
	}
	
	@ApiOperation(value = "兑换积分")
	@RequestMapping(value = "/exchangeIntegral", method = RequestMethod.POST)
	public StateResult exchangeIntegral (@RequestParam String userId,@RequestParam String orderSn,
			@RequestParam String productIds,@RequestParam long integralNum,@RequestParam int type,
			@RequestParam String shopName,@RequestParam String payChannel,@RequestParam String paytime,
			@RequestParam String sourceType) {
		
		ExeResult result = new ExeResult();

		if(StringUtil.isNullOrEmpty(userId)) {
			throw new IllegalArgumentException("用户userId不能为空");
		}
		if(StringUtil.isNullOrEmpty(orderSn)) {
			throw new IllegalArgumentException("订单号orderSn不能为空");
		}
		if(StringUtil.isNullOrEmpty(productIds)) {
			throw new IllegalArgumentException("商品productIds不能为空");
		}
		if(StringUtil.isNullOrEmpty(integralNum)) {
			throw new IllegalArgumentException("兑换积分数不能为空");
		}
		if(StringUtil.isNullOrEmpty(type)) {
			throw new IllegalArgumentException("兑换积分类型不能为空");
		}
		if(StringUtil.isNullOrEmpty(shopName)) {
			throw new IllegalArgumentException("消费门店不能为空");
		}
		if(StringUtil.isNullOrEmpty(payChannel)) {
			throw new IllegalArgumentException("支付方式不能为空");
		}
		if(StringUtil.isNullOrEmpty(paytime)) {
			throw new IllegalArgumentException("支付时间不能为空");
		}
		if(StringUtil.isNullOrEmpty(sourceType)) {
			throw new IllegalArgumentException("支付类型不能为空");
		}
		
		String tId = IdWorkerUtil.generateStringId();
		exchangeIntegralStrategy.exchangeIntegral(userId, tId, orderSn, productIds,
				integralNum, type, shopName, payChannel, paytime, sourceType);
		result.setErrCode(0);
		result.setSuccess(true);
		result.setMessage(IntegralConstants.SUCCESS);
		return result.getResult();
	}
	
	@ApiOperation(value = "测试微信消息推送")
	@RequestMapping(value = "/sendIntegralMsgToWx", method = RequestMethod.POST)
	public void sendIntegralMsgToWx(@RequestParam String openId) {
		if(StringUtil.isNullOrEmpty(openId)) {
			throw new IllegalArgumentException("用户openId不能为空");
		}
		integralService.sendIntegralMsgToWx(openId,"");
	}
}
