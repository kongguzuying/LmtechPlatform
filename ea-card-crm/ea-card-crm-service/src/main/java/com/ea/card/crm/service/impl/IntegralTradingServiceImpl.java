package com.ea.card.crm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.constants.IntegralConstants;
import com.ea.card.crm.dao.IntegralTradingRecordDao;
import com.ea.card.crm.facade.request.IntegralTradingRequest;
import com.ea.card.crm.facade.response.GetIntegralData;
import com.ea.card.crm.facade.response.GetIntegralResult;
import com.ea.card.crm.model.IntegralTradingRecord;
import com.ea.card.crm.service.IntegralService;
import com.ea.card.crm.service.IntergralTradingService;
import com.ea.card.crm.service.exception.IntegralConsumeException;
import com.ea.card.crm.service.exception.NoEnoughIntegralException;
import com.ea.card.crm.service.vo.RefreshSettleData;
import com.ea.card.crm.service.vo.ZeroMenoyPayData;
import com.lmtech.common.StateResult;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import com.lmtech.util.DateUtil;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

@SuppressWarnings("serial")
@Service
@RefreshScope
public class IntegralTradingServiceImpl extends AbstractDbManagerBaseImpl<IntegralTradingRecord> implements IntergralTradingService {
	
	@Autowired
	private IntegralService integralService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private IntegralTradingRecordDao integralTradingRecordDao;

	@Value("${service.url_buyer_refresh_settle}")
	private String URL_BUYER_REFRESH_SETTLE;
	
	@Value("${service.url_buyer_submit_order_pre}")
	private String URL_BUYER_SUBMIT_ORDER_PRE;
	
	@Value("${service.url_zero_menoy_pay}")
	private String URL_ZERO_MENOY_PAY;
	
	@Value("${service.url_buyer_order_list}")
	private String URL_BUYER_ORDER_LIST;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Dao getDao() {
		return integralTradingRecordDao;
	}
	
	public String getURL_BUYER_REFRESH_SETTLE() {
		return URL_BUYER_REFRESH_SETTLE;
	}

	public void setURL_BUYER_REFRESH_SETTLE(String uRL_BUYER_REFRESH_SETTLE) {
		URL_BUYER_REFRESH_SETTLE = uRL_BUYER_REFRESH_SETTLE;
	}

	public String getURL_BUYER_SUBMIT_ORDER_PRE() {
		return URL_BUYER_SUBMIT_ORDER_PRE;
	}

	public void setURL_BUYER_SUBMIT_ORDER_PRE(String uRL_BUYER_SUBMIT_ORDER_PRE) {
		URL_BUYER_SUBMIT_ORDER_PRE = uRL_BUYER_SUBMIT_ORDER_PRE;
	}

	public String getURL_ZERO_MENOY_PAY() {
		return URL_ZERO_MENOY_PAY;
	}

	public void setURL_ZERO_MENOY_PAY(String uRL_ZERO_MENOY_PAY) {
		URL_ZERO_MENOY_PAY = uRL_ZERO_MENOY_PAY;
	}
	
	public String getURL_BUYER_ORDER_LIST() {
		return URL_BUYER_ORDER_LIST;
	}

	public void setURL_BUYER_ORDER_LIST(String uRL_BUYER_ORDER_LIST) {
		URL_BUYER_ORDER_LIST = uRL_BUYER_ORDER_LIST;
	}

	@Override
	public IntegralTradingRecord consumerProduct(IntegralTradingRequest request) {
		
		//查询积分值
		GetIntegralResult integralResult = integralService.getIntegral(request.getUserId());
		GetIntegralData integralData= integralResult.getData();
		long integralNumber = integralData.getSumIntegralNumber();
		//积分不足
		if(integralNumber<=request.getConsumptionIntegral()) {
			throw new NoEnoughIntegralException();
		}
		
		//提交订单
		RefreshSettleData refreshSettleData = new RefreshSettleData();
		refreshSettleData.setGoodslist(request.getGoodslist());
		refreshSettleData.setAmount(request.getAmount());
		refreshSettleData.setSettletype(IntegralConstants.ONE);
		refreshSettleData.setToken(request.getToken());
		refreshSettleData.setReceiverinfoid(request.getReceiverinfoId());
		refreshSettleData.setSource(IntegralConstants.THERE);
		String orderSn = submitOrder(refreshSettleData,request.getOpenId(), request.getConsumptionIntegral());
		
		//扣除积分
		integralService.consumeIntegral(request.getUserId(), request.getConsumptionIntegral(), IntegralConstants.SOURCE_FIVE);
//		StringBuffer ordersNo = new StringBuffer();
		//0元支付，订单状态修改为代发货
		ZeroMenoyPayData zeroMenoyPayData = new ZeroMenoyPayData();
//		for(int z=0;z<orderSnList.size();z++) {
//			Map<String, String> orderSn = (Map<String, String>) orderSnList.get(z);
//			if(z!=0) 
//				ordersNo.append("|");//  “|”  分割
//			ordersNo.append(zeroMenoyPayData.getOrderSn());
			
		zeroMenoyPayData.setOrderSn(orderSn);
		zeroMenoyPayData.settId(request.gettId());
		zeroMenoyPayData.setUserId(request.getUserId());
		zeroMenoyPay(zeroMenoyPayData);
			
//		}
		
		//记录积分兑换流水
		IntegralTradingRecord record = new IntegralTradingRecord();
		record.setTid(request.gettId());
		record.setUserId(request.getUserId());
		record.setOrderNo(orderSn);
		record.setConsumptionPoints(request.getConsumptionIntegral());
		record.setBeforeConsumptionPoints(integralNumber);
		record.setRemainingPoints(integralNumber-request.getConsumptionIntegral());
		record.setRemarkMsg(IntegralConstants.SUCCESS);
		String id = exchangeProductRecord(record);
		if(StringUtil.isNullOrEmpty(id)) 
			throw new IntegralConsumeException(ErrorConstants.ERR_INTEGRAL_TRADING_INSERT_ERROR_MSG, ErrorConstants.ERR_INTEGRAL_TRADING_INSERT_ERROR);
		return record;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String submitOrder(RefreshSettleData refreshSettleData, String openId, long consumptionIntegral) {
		//购物车结算刷新接口
		MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("token", refreshSettleData.getToken());
		requestMap.add("goodslist", refreshSettleData.getGoodslist());
		requestMap.add("settletype", refreshSettleData.getSettletype());
		requestMap.add("amount", refreshSettleData.getAmount());
		requestMap.add("source", refreshSettleData.getSource());
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(requestMap, null);
		StateResult stateResult = restTemplate.postForObject(URL_BUYER_REFRESH_SETTLE, request, StateResult.class);
		if(stateResult.getState()!=0)//接口请求异常
			throw new IntegralConsumeException(stateResult.getMsg(), stateResult.getState());
		//打包入参
		LinkedHashMap<String, Object> dataObj = (LinkedHashMap<String, Object>) stateResult.getData();
		List storeArray = (List) dataObj.get("storeList");
		Map<String, Object> preordersinfo = new HashMap<String, Object>();
		List preOrders = new ArrayList();
		for(int i=0;i<storeArray.size();i++) {
			LinkedHashMap<String, Object> store = (LinkedHashMap<String, Object>) storeArray.get(i);
			List preOrderList = (List) store.get("preOrderList");
			for(int j=0;j<preOrderList.size();j++) {
				LinkedHashMap<String, Object> preOrder = (LinkedHashMap<String, Object>) preOrderList.get(i);
				Map<String, String> order = new HashMap<String, String>();
				order.put("preOrderSN", preOrder.get("preOrderSN").toString());
				order.put("deliverySeq", preOrder.get("defaultDeliverySeq").toString());
				order.put("comment", "");
				preOrders.add(order);
			}
		}
		preordersinfo.put("preOrders", preOrders);
		MultiValueMap<String, Object> submitPreOrderMap = new LinkedMultiValueMap<>();
		submitPreOrderMap.add("integralnum", consumptionIntegral);
		submitPreOrderMap.add("preordersinfo", preordersinfo);
		submitPreOrderMap.add("receiverinfoid", refreshSettleData.getReceiverinfoid());
		submitPreOrderMap.add("receipt", IntegralConstants.ZERO);
		submitPreOrderMap.add("openid", openId);
		submitPreOrderMap.add("paytype", IntegralConstants.ZERO);
		submitPreOrderMap.add("settletype", IntegralConstants.ONE);
		submitPreOrderMap.add("source", IntegralConstants.THERE);
		submitPreOrderMap.add("token", refreshSettleData.getToken());
		HttpEntity<MultiValueMap<String, Object>> submitPreOrder = new HttpEntity<>(submitPreOrderMap, null);
		reInitMessageConverter(restTemplate);
		//结算单提交接口
		String result = restTemplate.postForObject(URL_BUYER_SUBMIT_ORDER_PRE, submitPreOrder, String.class);
		JSONObject resultObj = JSONObject.parseObject(result);
		if(resultObj.getInteger("state")!=0) //接口请求异常
			throw new IntegralConsumeException(resultObj.getString("msg"), resultObj.getInteger("state"));
		JSONObject orderData = resultObj.getJSONObject("data");
		return orderData.getString("tradeNo");
	}

	@Override
	public void zeroMenoyPay(ZeroMenoyPayData zeroMenoyPayData) {
		MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("order_sn", zeroMenoyPayData.getOrderSn());
		requestMap.add("t_id", zeroMenoyPayData.gettId());
		requestMap.add("user_id", zeroMenoyPayData.getUserId());
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(requestMap, null);
		StateResult stateResult = restTemplate.postForObject(URL_ZERO_MENOY_PAY, request, StateResult.class);
		if(stateResult.getState()!=0) //接口请求异常
			throw new IntegralConsumeException(stateResult.getMsg(), stateResult.getState());
	}
	
	@Override
	public String exchangeProductRecord(IntegralTradingRecord record) {
//		return integralTradingRecordDao.insert(record);
		return addObjectEntity(record);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public LinkedHashMap<String, Object> exchangeProductList(String token, String pageNum, String pageSize,String userId) throws ParseException {
		MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("token", token);
		requestMap.add("pagenum", pageNum);
		requestMap.add("pagesize", pageSize);
		requestMap.add("orderstatus", IntegralConstants.ZERO);
		requestMap.add("source", IntegralConstants.THERE);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(requestMap, null);
		StateResult result = restTemplate.postForObject(URL_BUYER_ORDER_LIST, request,StateResult.class);
		if(result.getState()!=0) 
			throw new IntegralConsumeException(result.getMsg(), result.getState());
		
		LinkedHashMap<String, Object> dataMap = (LinkedHashMap<String, Object>) result.getData();
		List orderList = (List) dataMap.get("orderList");
		//获取订单号集合
		List<String> orderSnList = new ArrayList<String>();
		for(int i=0; i<orderList.size(); i++) {
			LinkedHashMap<String, Object> orderMap = (LinkedHashMap<String, Object>) orderList.get(i);
			orderSnList.add(orderMap.get("orderSN").toString()); 
		}
		List<IntegralTradingRecord> recordList = integralTradingRecordDao.selectListByOrderNo(userId, orderSnList);
		//重新打包结果集
		List resultList = new ArrayList();
		Map<String,IntegralTradingRecord> recordMap = new HashMap<String,IntegralTradingRecord>();
		for(IntegralTradingRecord record : recordList) {
			recordMap.put(record.getOrderNo(),record);
		}
		for(int o=0; o<orderList.size(); o++) {
			LinkedHashMap<String, Object> orderMap = (LinkedHashMap<String, Object>) orderList.get(o);
			IntegralTradingRecord itr = recordMap.get(orderMap.get("orderSN").toString());
			orderMap.put("orderTime", DateUtil.strToDateFormat(orderMap.get("orderTime").toString()));
			if(itr!=null) {
				//消费前积分
				orderMap.put("beforeConsumptionPoints", itr.getBeforeConsumptionPoints());
				//商品消费积分
				orderMap.put("consumptionPoints", itr.getConsumptionPoints());
				//消费后积分
				orderMap.put("remainingPoints", itr.getRemainingPoints());
			}else {
				orderMap.put("beforeConsumptionPoints", "");
				orderMap.put("consumptionPoints", "");
				orderMap.put("remainingPoints", "");
			}
			resultList.add(orderMap);
		}
		dataMap.put("orderList",resultList);
		return dataMap;
	}

	
	/*
     *初始化RestTemplate，RestTemplate会默认添加HttpMessageConverter
     * 添加的StringHttpMessageConverter非UTF-8
     * 所以先要移除原有的StringHttpMessageConverter，
     * 再添加一个字符集为UTF-8的StringHttpMessageConvert
     * */
     private void reInitMessageConverter(RestTemplate restTemplate){
         List<HttpMessageConverter<?>> converterList=restTemplate.getMessageConverters();
         HttpMessageConverter<?> converterTarget = null;
         for (HttpMessageConverter<?> item : converterList) {
             if (item.getClass() == StringHttpMessageConverter.class) {
                 converterTarget = item;
                 break;
             }
         }

         if (converterTarget != null) {
             converterList.remove(converterTarget);
         }
         HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
         converterList.add(converter);
     }
}
