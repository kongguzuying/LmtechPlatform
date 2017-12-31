package com.ea.card.trade.facade;

import com.ea.card.trade.stub.NotifyFacade;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Api(description = "交易通知接口")
@RestController
@RequestMapping(value = "/notify")
public class NotifyFacadeImpl implements NotifyFacade {
    @Override
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            LoggerManager.info("接收微信通知 => 开始");
            String body = this.getRequestBody(request);
            LoggerManager.info("通知Body：" + body);

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
