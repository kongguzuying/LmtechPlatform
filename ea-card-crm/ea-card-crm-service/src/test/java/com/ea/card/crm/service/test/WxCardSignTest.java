package com.ea.card.crm.service.test;

import com.ea.card.crm.service.util.WxCardSign;
import org.junit.Test;

public class WxCardSignTest {

    @Test
    public void testGetCardSign() {
        String ticket = "IpK_1T69hDhZkLQTlwsAXxOEHxyIS_A2vyrb2Shlx4OkvGVQ5aKAL6tpBrjGU2Gt-ptabwX8n43LYDxE8kL8gQ";
        String timestamp = "1502829071";
        String cardId = "string";
        String code = "string";
        String openId = "string";
        String noncestr = "195f801f-684c-446c-93cb-34cba0cfc87c";

        WxCardSign sign = new WxCardSign();
        sign.addData(ticket);
        sign.addData(timestamp);
        sign.addData(cardId);
        sign.addData(code);
        sign.addData(openId);
        sign.addData(noncestr);
        String signature = sign.getSignature();

        System.out.println(signature);
    }
}
