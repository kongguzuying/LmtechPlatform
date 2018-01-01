package com.ea.card.trade.facade;

import com.ea.card.crm.facade.stub.IntegralTradingFacade;
import com.ea.card.crm.facade.stub.MemberFacade;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.trade.stub.NotifyFacade;
import com.ea.card.trade.vo.PayNoticeData;
import com.lmtech.common.StateResult;
import com.lmtech.common.StateResultT;
import com.lmtech.util.DateUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import com.lmtech.util.XmlUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

@Api(description = "交易通知接口")
@RestController
@RequestMapping(value = "/notify")
public class NotifyFacadeImpl implements NotifyFacade {

    @Autowired
    private IntegralTradingFacade integralTradingFacade;
    @Autowired
    private MemberFacade memberFacade;

    @Override
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            LoggerManager.info("接收微信通知 => 开始");
            String body = this.getRequestBody(request);
            LoggerManager.info("通知Body：" + body);

            PayNoticeData noticeData = XmlUtil.fromXml(body, "xml", PayNoticeData.class);

            //增加积分
            this.scanPayIncreaseIntegral(noticeData);

            //TODO 微信通知处理，改变数据状态 需要用锁机制避免多次通知
            response.getWriter().write("<xml>" +
                    "<return_code><![CDATA[SUCCESS]]></return_code>" +
                    "<return_msg><![CDATA[OK]]></return_msg>" +
                    "</xml>");
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            LoggerManager.info("接收微信通知 => 结束");
        }
    }

    /**
     * 扫码支付增加积分
     * @param noticeData
     */
    private void scanPayIncreaseIntegral(PayNoticeData noticeData) {
        String openId = noticeData.getOpenid();
        String orderId = noticeData.getOut_trade_no();
        String proId = "0";
        int totalAmount = noticeData.getTotal_fee() / 100;
        String payTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        String sourceType = "2";
        String shopName = "none";

        StateResultT<MemberRegister> result = memberFacade.getByWxActiveOpenId(openId);
        if (result.isSuccess() && result.getData() != null) {
            MemberRegister register = result.getData();
            String uniqueId = register.getUniqueId();
            StateResult integralResult = integralTradingFacade.exchangeIntegral(uniqueId, orderId, proId, totalAmount, 1, shopName, "1", payTime, sourceType);
            if (integralResult.isSuccess()) {
                LoggerManager.info("用户：" + register.getNickname() + "扫码支付增加积分成功，共" + totalAmount + "个积分。");
            } else {
                LoggerManager.error("用户：" + register.getNickname() + "扫码支付增加积分失败，" + integralResult.getMsg());
            }
        } else {
            LoggerManager.debug("通过公众号openid获取用户信息失败，用户未注册");
        }
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String line;
        StringBuilder sb = new StringBuilder();
        do {
            line = reader.readLine();
            if (!StringUtil.isNullOrEmpty(line)) {
                sb.append(line);
            }
        } while (!StringUtil.isNullOrEmpty(line));
        return sb.toString();
    }
}
