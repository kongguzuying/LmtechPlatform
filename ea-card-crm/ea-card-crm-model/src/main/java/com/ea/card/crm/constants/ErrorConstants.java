package com.ea.card.crm.constants;

public class ErrorConstants {
    /** 未知错误 **/
    public static final long ERR_UNKNOW = 3600000;
    public static final String ERR_UNKNOW_MSG = "系统繁忙";

    /** 系统参数错误 **/
    public static final long ERR_ARG_ERROR = 3600002;
    public static final String ERR_ARG_ERROR_MSG = "系统参数错误";

    /** 微信接口调用异常 **/
    public static final long ERR_WX_INVOKEAPI_ERROR = 3600004;
    public static final String ERR_WX_INVOKEAPI_ERROR_MSG = "微信接口调用异常";

    /** UC接口调用异常 **/
    public static final long ERR_UC_INVOKEAPI_ERROR = 3600006;
    public static final String ERR_UC_INVOKEAPI_ERROR_MSG = "UC接口调用异常";

    /** 无权限查看 **/
    public static final long ERR_NO_PERMISSION_VIEW = 3600008;
    public static final String ERR_NO_PERMISSION_VIEW_MSG = "无权限查看";




    /** 手机号已注册 **/
    public static final long ERR_UC_REGISTER_PHONE_EXIST = 3200202;
    public static final String ERR_UC_REGISTER_PHONE_EXIST_MSG = "手机号已注册";

    /** 手机号或微信重复激活 **/
    public static final long ERR_CRM_ACTIVE_REPEAT = 3502004;
    public static final String ERR_CRM_ACTIVE_REPEAT_MSG = "手机号或微信重复激活";

    /** 会员等级已同级或更高 **/
    public static final long ERR_CRM_TRIAL_LEVEL_HIGHER = 3502006;
    public static final String ERR_CRM_TRIAL_LEVEL_HIGHER_MSG = "会员等级已同级或更高";

    /** 会员已试用 **/
    public static final long ERR_CRM_HAS_TRIAL = 3502006;
    public static final String ERR_CRM_HAS_TRIAL_MSG = "会员已试用";

    /** 生成礼品会员卡订单出错 **/
    public static final long ERR_CRM_GEN_GIFT_CARD = 3502008;
    public static final String ERR_CRM_GEN_GIFT_CARD_MSG = "生成礼品会员卡订单出错";

    /** 生成礼品会员卡订单出错，支付记录不存在 **/
    public static final long ERR_CRM_GEN_GIFT_CARD_RECORDNOTEXIST = 35020010;
    public static final String ERR_CRM_GEN_GIFT_CARD_RECORDNOTEXIST_MSG = "支付记录不存在";

    /** 生成礼品会员卡订单出错，订单未支付 **/
    public static final long ERR_CRM_GEN_GIFT_CARD_NOPAY = 35020012;
    public static final String ERR_CRM_GEN_GIFT_CARD_NOPAY_MSG = "订单未支付";

    /** 生成礼品会员卡订单出错，订单支付失败 **/
    public static final long ERR_CRM_GEN_GIFT_CARD_PAYFAILED = 35020014;
    public static final String ERR_CRM_GEN_GIFT_CARD_PAYFAILED_MSG = "订单支付失败";

    /** 不存在中奖商品 **/
    public static final long ERR_CRM_PRIZE_PRODUCT_NOTEXIST = 35020014;
    public static final String ERR_CRM_PRIZE_PRODUCT_NOTEXIST_MSG = "不存在中奖商品";

    /** 不存在没中奖商品 **/
    public static final long ERR_CRM_NOPRIZE_PRODUCT_NOTEXIST = 35020014;
    public static final String ERR_CRM_NOPRIZE_PRODUCT_NOTEXIST_MSG = "不存在没中奖商品";

    /** 订单不存在 **/
    public static final long ERR_CRM_ORDER_NOTEXIST = 35020016;
    public static final String ERR_CRM_ORDER_NOTEXIST_MSG = "订单不存在";

    /** 订单中不存在礼品卡 **/
    public static final long ERR_CRM_GIFTCARD_NOTEXIST_INORDER = 35020018;
    public static final String ERR_CRM_GIFTCARD_NOTEXIST_INORDER_MSG = "订单中不存在礼品卡";

    /** 礼品卡已领完 **/
    public static final long ERR_CRM_NOCARDFORRECEIVE = 35020020;
    public static final String ERR_CRM_NOCARDFORRECEIVE_MSG = "礼品卡已领完";

    /** 礼品卡已领完 **/
    public static final long ERR_CRM_NONEED_INCREASE_LEVEL = 35020022;
    public static final String ERR_CRM_NONEED_INCREASE_LEVEL_MSG = "会员等级已没有必要再提升";

    /** 卡片赠送中，无法继续赠送 **/
    public static final long ERR_CRM_CARD_PRESENTING = 35020024;
    public static final String ERR_CRM_CARD_PRESENTING_MSG = "卡片赠送中，无法继续赠送";

    /** 用户校验出错 **/
    public static final long ERR_CRM_USER_CHECK = 35020026;
    public static final String ERR_CRM_USER_CHECK_MSG = "用户校验出错";

    /** 不存在未赠送的卡片 **/
    public static final long ERR_CRM_NOT_EXIST_NOPRESENT_CARD = 35020026;
    public static final String ERR_CRM_NOT_EXIST_NOPRESENT_CARD_MSG = "不存在未赠送的卡片";

    /** 不存在赠送卡片记录 **/
    public static final long ERR_CRM_NOT_EXIST_CARD_PRESENT_RECORD = 35020028;
    public static final String ERR_CRM_NOT_EXIST_CARD_PRESENT_RECORD_MSG = "不存在赠送卡片记录";

    /** 不存在购买卡记录明细 **/
    public static final long ERR_CRM_NOT_EXIST_CARD_PAY_DETAIL = 35020030;
    public static final String ERR_CRM_NOT_EXIST_CARD_PAY_DETAIL_MSG = "不存在购买卡记录明细";

    /** 不存在购买卡记录明细 **/
    public static final long ERR_CRM_NOT_EXIST_CARD_ACTIVE_RECORD = 35020030;
    public static final String ERR_CRM_NOT_EXIST_CARD_ACTIVE_RECORD_MSG = "不存在激活授权记录";

    /** 订单金额校验失败 **/
    public static final long ERR_CRM_ORDER_AMOUNT_CHECK = 35020032;
    public static final String ERR_CRM_ORDER_AMOUNT_CHECK_MSG = "订单金额校验失败";


    /** 订单支付状态异常 **/
    public static final long ERR_CRM_ORDER_PAY_STATUS = 35020034;
    public static final String ERR_CRM_ORDER_PAY_STATUS_MSG = "订单支付状态异常";

    /** 订单支付状态异常 **/
    public static final long ERR_CRM_REMOTE_DATE_ERROR = 35020036;
    public static final String ERR_CRM_REMOTE_DATE_ERROR_MSG = "远程接口返回数据异常";

    /** Token过期异常 **/
    public static final long ERR_CRM_TOKEN_EXPIRE_ERROR = 35020038;
    public static final String ERR_CRM_TOKEN_EXPIRE_ERROR_MSG = "当前用户未登录";

    /** 订单参数异常 **/
    public static final long ERR_CRM_ORDER_PAY_PARAMETER = 35020040;
    public static final String ERR_CRM_ORDER_PAY_PARAMETER_MSG = "订单参数异常";


    /** 微信页面授权错误 **/
    public static final long ERR_WX_PAGE_AUTH_ERROR = 3503000;
    public static final String ERR_WX_PAGE_AUTH_ERROR_MSG = "微信页面授权错误";

    /** 微信页面授权刷新错误 **/
    public static final long ERR_WX_PAGE_AUTH_REFRESH_ERROR = 3503002;
    public static final String ERR_WX_PAGE_AUTH_REFRESH_ERROR_MSG = "微信页面授权刷新错误";

    /** 微信用户信息过期错误 **/
    public static final long ERR_WX_USER_EXPIRE_ERROR = 3503004;
    public static final String ERR_WX_USER_EXPIRE_ERROR_MSG = "微信用户信息过期错误";



    /** 积分不足 **/
    public static final long ERR_INTEGRAL_LACK_ERROR = 3504000;
    public static final String ERR_INTEGRAL_LACK_ERROR_MSG = "积分不足";
    
    /** 积分兑换商品记录操作插入失败 **/
    public static final long ERR_INTEGRAL_TRADING_INSERT_ERROR = 3504001;
    public static final String ERR_INTEGRAL_TRADING_INSERT_ERROR_MSG = "积分兑换商品记录操作插入失败";
    
    /** 积分类型不正确 **/
    public static final long ERR_INTEGRAL_TYPE_ERROR = 3504002;
    public static final String ERR_INTEGRAL_TYPE_ERROR_MSG = "积分类型不正确";
    
    /** 积分兑换商品记录操作插入失败 **/
    public static final long ERR_EXCHANGE_INTEGRAL_INSERT_ERROR = 3504003;
    public static final String ERR_EXCHANGE_INTEGRAL_INSERT_ERROR_MSG = "兑换积分流水记录操作插入失败";

    /** 发送短信异常 **/
    public static final long ERR_SEND_SMS_ERROR = 3504003;
    public static final String ERR_SEND_SMS_ERROR_MSG = "发送短信出错";
}
