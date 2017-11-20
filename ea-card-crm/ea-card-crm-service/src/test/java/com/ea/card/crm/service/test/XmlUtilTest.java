package com.ea.card.crm.service.test;

import com.ea.card.crm.service.vo.WxMessageBase;
import com.lmtech.util.XmlUtil;
import org.junit.Test;

public class XmlUtilTest {

    @Test
    public void testXmlUtil1() {


        String xml = "<xml><ToUserName><![CDATA[gh_5b60e29a0106]]></ToUserName>" +
                "<FromUserName><![CDATA[o-yJVw1_-xijGq5Sl4U0JMr3RBFw]]></FromUserName>" +
                "<CreateTime>1502963529</CreateTime>" +
                "<MsgType><![CDATA[event]]></MsgType>" +
                "<Event><![CDATA[unsubscribe]]></Event>" +
                "<EventKey><![CDATA[]]></EventKey>" +
                "</xml>";

        WxMessageBase messageBase = XmlUtil.fromXml(xml, "xml", WxMessageBase.class);

        System.out.println(messageBase);
    }

    @Test
    public void testXmlUtil2() {
        String xml = "<xml><ToUserName><![CDATA[gh_5b60e29a0106]]></ToUserName>" +
        "<FromUserName><![CDATA[o-yJVwyzcJhjcCWHvmzof1BHLDT4]]></FromUserName>" +
        "<CreateTime>1502966405</CreateTime>" +
        "<MsgType><![CDATA[event]]></MsgType>" +
        "<Event><![CDATA[user_view_card]]></Event>" +
        "<CardId><![CDATA[p-yJVw_Vv3Lcba419cpTcLuDp-D0]]></CardId>" +
        "<UserCardCode><![CDATA[253506177178]]></UserCardCode>" +
        "<OuterStr><![CDATA[h5]]></OuterStr>" +
        "</xml>";

        WxMessageBase messageBase = XmlUtil.fromXml(xml, "xml", WxMessageBase.class);

        System.out.println(messageBase);
    }
}
