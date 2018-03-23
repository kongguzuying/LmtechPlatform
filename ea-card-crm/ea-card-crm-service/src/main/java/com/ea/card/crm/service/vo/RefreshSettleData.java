package com.ea.card.crm.service.vo;

import java.util.List;

/**
 * 订单提交
 * @author huacheng.li
 *
 */
public class RefreshSettleData {
	
	//商城登录成功返回的 token
	private String token;
	
	//商品列表
	@SuppressWarnings("rawtypes")
	private List goodslist;
	
	//收货地址Id
	private String receiverinfoid;
	
	//为空默认普通结算，1=直接购买（进入直接购买流程，） 2=团购（参与团购活动）
	private int settletype;
	
	//settletype为1时，即立即购买的商品数量，不传默认为数量为1
	private int amount;
	
	//拼团id,开团后为必传参数
	private String grouponid;
	
	//来源渠道（0：app分享 1：wap分享 2：广告渠道 3.三好节  4.xxx推广渠道  5.若干渠道等等 默认值：0）
	private String sharechannelid;
	
	//1.朋友圈 2.朋友 3. 新浪微博 4.QQ好友 5.QQ空间 6.手机联系人 7.二维码 8.复制链接 
	private String sharesubchannelid;
	
	//渠道索引 （0 到 20的数字）
	private String channelindex;
	
	//为空时默认生活购物车， 1:友店购物车（暂时不用，已有友店购物车接口） 2：尊享商城购物车
	private int source;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@SuppressWarnings("rawtypes")
	public List getGoodslist() {
		return goodslist;
	}

	@SuppressWarnings("rawtypes")
	public void setGoodslist(List goodslist) {
		this.goodslist = goodslist;
	}

	public String getReceiverinfoid() {
		return receiverinfoid;
	}

	public void setReceiverinfoid(String receiverinfoid) {
		this.receiverinfoid = receiverinfoid;
	}

	public int getSettletype() {
		return settletype;
	}

	public void setSettletype(int settletype) {
		this.settletype = settletype;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getGrouponid() {
		return grouponid;
	}

	public void setGrouponid(String grouponid) {
		this.grouponid = grouponid;
	}

	public String getSharechannelid() {
		return sharechannelid;
	}

	public void setSharechannelid(String sharechannelid) {
		this.sharechannelid = sharechannelid;
	}

	public String getSharesubchannelid() {
		return sharesubchannelid;
	}

	public void setSharesubchannelid(String sharesubchannelid) {
		this.sharesubchannelid = sharesubchannelid;
	}

	public String getChannelindex() {
		return channelindex;
	}

	public void setChannelindex(String channelindex) {
		this.channelindex = channelindex;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
	
	
	
	
}
