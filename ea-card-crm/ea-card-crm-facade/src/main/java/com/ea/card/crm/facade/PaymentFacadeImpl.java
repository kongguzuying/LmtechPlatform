package com.ea.card.crm.facade;

import com.ea.card.crm.facade.request.*;
import com.ea.card.crm.facade.stub.PaymentFacade;
import com.ea.card.crm.model.CardPayRecord;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.model.RechargePayRecord;
import com.ea.card.crm.service.*;
import com.ea.card.crm.service.exception.NoneRegisterException;
import com.ea.card.crm.service.exception.RechargePayException;
import com.ea.card.crm.service.vo.BalanceLockResult;
import com.ea.card.crm.service.vo.RechargePayResult;
import com.lmtech.common.ContextManager;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.infrastructure.facade.response.CodeItemListResponse;
import com.lmtech.infrastructure.facade.stub.CodeFacade;
import com.lmtech.infrastructure.model.CodeItem;
import com.lmtech.infrastructure.model.CodeType;
import com.lmtech.util.IdWorkerUtil;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "支付接口")
@RestController
@RequestMapping(value = "/payment")
public class PaymentFacadeImpl implements PaymentFacade {

    @Autowired
    private WxService wxService;
    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RechargePayRecordService rechargePayRecordService;
    @Autowired
    private CardPayRecordService cardPayRecordService;
    @Autowired
    private GiftMemberCardService giftMemberCardService;
    @Autowired
    private CodeFacade codeFacade;

    @ApiOperation(value = "会员卡充值订单生成")
    @Override
    public StateResult rechargeRequest(@RequestBody RechargePayRequest request) {
        RechargePayRecord record = null;
        ExeResult result = new ExeResult();
        try {
            if (StringUtil.isNullOrEmpty(request.getOpenId())) {
                throw new IllegalArgumentException("传入openid为空");
            }
            if (request.getTotalAmount() <= 0) {
                throw new IllegalArgumentException("传入金额为0");
            }

            MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());

            if (register == null) {
                throw new IllegalArgumentException("传入openid的关联用户还未注册");
            }

            record = new RechargePayRecord();
            ContextManager.setValue(RechargePayRecord.CONTEXT_KEY, record);
            String tid = IdWorkerUtil.generateStringId();
            String orderNo = paymentService.rechargeRequest(tid, register.getUserId(), register.getPhone(), request.getTotalAmount(), PaymentService.ORDER_REQUEST_TYPE_RECHARGE);
            Map<String, Object> data = new HashMap<>();
            data.put("orderNo", orderNo);
            result.setSuccess(true);
            result.setData(data);
            return result.getResult();
        } catch (Exception e) {
            record.setStatus(RechargePayRecord.STATUS_ERROR);
            record.setErrMsg(e.getMessage());
            throw e;
        } finally {
            if (record != null) {
                rechargePayRecordService.add(record);
            }
        }
    }

    @ApiOperation(value = "会员卡充值支付")
    public StateResult rechargePay(@RequestBody RechargePayRequest request) {
        RechargePayRecord record = null;
        ExeResult result = new ExeResult();


        try {
            if (StringUtil.isNullOrEmpty(request.getOpenId())) {
                throw new IllegalArgumentException("传入openid为空");
            }
            if (request.getTotalAmount() <= 0) {
                throw new IllegalArgumentException("传入金额为0");
            }

            MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());

            if (register == null) {
                throw new IllegalArgumentException("传入openid的关联用户还未注册");
            }

            record = new RechargePayRecord();
            ContextManager.setValue(RechargePayRecord.CONTEXT_KEY, record);
            String tid = IdWorkerUtil.generateStringId();
            String orderNo = paymentService.rechargeRequest(tid, register.getUserId(), register.getPhone(), request.getTotalAmount(), PaymentService.ORDER_REQUEST_TYPE_RECHARGE);
            RechargePayResult stateResult = paymentService.rechargePayment(tid, register.getUserId(), register.getPhone(), orderNo, request.getOfficialOpenId());
            if (stateResult.isSuccess()) {
                Map<String, String> signResult = wxService.getPaySign(stateResult.getData().getPrepayId());
                signResult.put("orderNo", orderNo);
                result.setSuccess(true);
                result.setData(signResult);
                return result.getResult();
            } else {
                throw new RechargePayException(stateResult.getMsg(), stateResult.getState());
            }
        } catch (Exception e) {
            record.setStatus(RechargePayRecord.STATUS_ERROR);
            record.setErrMsg(e.getMessage());
            throw e;
        } finally {
            if (record != null) {
                rechargePayRecordService.add(record);
            }
        }
    }

    @ApiOperation(value = "礼品卡购买支付")
    @Override
    public StateResult giftCardPay(HttpServletRequest httpRequest, @Valid @RequestBody GiftCardPayRequest request) {
        CardPayRecord record = null;
        ExeResult result = new ExeResult();
        try {
            if (StringUtil.isNullOrEmpty(request.getOpenId())) {
                throw new IllegalArgumentException("传入openid为空");
            }
            /*if (request.getTotalAmount() <= 0) {
                throw new IllegalArgumentException("传入金额为0");
            }*/

            MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());

            if (register == null) {
                throw new IllegalArgumentException("传入openid的关联用户还未注册");
            }

            //订单参数校验 TODO
//            paymentService.payParamCheck(request);

            record = new CardPayRecord();
            record.setGiftRecord(JsonUtil.toJson(request));
            record.setSource(CardPayRecord.SOURCE_CARDPAY);
            ContextManager.setValue(CardPayRecord.CONTEXT_KEY, record);
            String tid = IdWorkerUtil.generateStringId();

            //String orderNo = paymentService.rechargeRequest(tid, register.getUserId(), register.getPhone(), request.getTotalAmount(), PaymentService.ORDER_REQUEST_TYPE_CARDPAY);
            //RechargePayResult stateResult = paymentService.rechargePayment(tid, register.getUserId(), register.getPhone(), orderNo, request.getOfficialOpenId());
            RechargePayResult stateResult = new RechargePayResult();
            stateResult.setState(0);
            String orderNo = IdWorkerUtil.generateStringId();
            if (stateResult.isSuccess()) {
                Map<String, String> signResult = wxService.getPaySign(stateResult.getData().getPrepayId());
                signResult.put("orderNo", orderNo);
                result.setSuccess(true);
                result.setData(signResult);
                return result.getResult();
            } else {
                throw new RechargePayException(stateResult.getMsg(), stateResult.getState());
            }
        } catch (Exception e) {
            record.setStatus(RechargePayRecord.STATUS_ERROR);
            record.setErrMsg(e.getMessage());
            throw e;
        } finally {
            if (record != null) {
                cardPayRecordService.add(record);
            }
        }
    }

    @ApiOperation(value = "购买礼品卡成功回调接口）")
    @Override
    public StateResult giftCardPaySuccess(@RequestBody GiftCardPaySuccessRequest request) {
        ExeResult result = new ExeResult();

        LoggerManager.info("礼品卡购买成功接口回调 => 开始");
        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
        if (register != null) {
            //TODO 作订单校验，如果订单状态已完成则跳过等
            CardPayRecord payRecord = cardPayRecordService.getByOrderNo(request.getOrderNo());

            //TODO 校验订单
//                paymentService.checkOrderPaySuccess(request.getOrderNo());

            giftMemberCardService.genGiftCardOfOrder(request.getOrderNo());
            LoggerManager.info("礼品卡购买成功接口回调 => 结束");

            GiftCardPayRequest payRequest = (GiftCardPayRequest) JsonUtil.fromJson(payRecord.getGiftRecord(), GiftCardPayRequest.class);
            payRequest.setPrePresentRecordId(IdWorkerUtil.generateStringId());

            CodeItemListResponse response = codeFacade.getCodeItemOfType(new StringRequest(CodeType.CODE_MEMBER_CARD));
            List<CodeItem> codeItemList = response.getData();
            for (CodeItem codeItem : codeItemList) {
                if (codeItem.getValue().equals(String.valueOf(payRequest.getCardLevel()))) {
                    payRequest.setCardLevelName(codeItem.getName());
                    break;
                }
            }
//                payRequest.setCardLevelName(payRequest.getCardLevel() == 2 ? "Vpass尊享会员" : "普通会员");

            result.setSuccess(true);
            result.setData(payRequest);
        } else {
            throw new NoneRegisterException();
        }

        return result.getResult();
    }

    @ApiOperation(value = "查询余额")
    @Override
    public Object getBalance(@RequestParam(value = "openId", required = true) String openId) {

        MemberRegister memberRegister = memberRegisterService.getByOpenId(openId);
        if (memberRegister != null) {
            String tid = IdWorkerUtil.generateStringId();
            //String balanceResult = paymentService.getBalance(tid, memberRegister.getUserId(), memberRegister.getPhone());

            Map<String, Object> balanceResult = new HashMap<>();
            balanceResult.put("hasPayPwd", 1);
            balanceResult.put("myBalance", 0);
            balanceResult.put("state", 0);
            return balanceResult;
        } else {
            throw new NoneRegisterException();
        }
    }

    @ApiOperation(value = "充值成功接口")
    @Override
    public StateResult rechargeSuccess(@Valid @RequestBody AddBalanceRequest request) {

        LoggerManager.info("充值成功接口回调 => 开始");
        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
        if (register != null) {

            //校验微信订单
//                paymentService.checkOrderPaySuccess(request.getOrderNo());

            String tid = IdWorkerUtil.generateStringId();
            StateResult stateResult = paymentService.rechargeSuccess(tid, register.getUserId(), register.getPhone(), request.getOrderNo());
            LoggerManager.info("充值成功接口回调 => 结束");
            return stateResult;
        } else {
            throw new NoneRegisterException();
        }
    }

    @ApiOperation(value = "交易记录列表")
    @Override
    public Object recordList(@RequestParam String openId, @RequestParam(required = false, defaultValue = "0") int pageIndex, @RequestParam(required = false, defaultValue = "0") int pageSize) {

        MemberRegister memberRegister = memberRegisterService.getByOpenId(openId);
        if (memberRegister != null) {
            pageIndex = (pageIndex <= 0 ? 1 : pageIndex);
            pageSize = (pageSize <= 0 ? 10 : pageSize);
            String tid = IdWorkerUtil.generateStringId();
            String balanceListResult = paymentService.recordList(tid, memberRegister.getUserId(), memberRegister.getPhone(), pageIndex, pageSize);
            return balanceListResult;
        } else {
            throw new NoneRegisterException();
        }
    }

    @ApiOperation(value = "交易记录详情")
    @Override
    public Object recordDetail(@RequestParam String recordId, @RequestParam String openId) {

        MemberRegister memberRegister = memberRegisterService.getByOpenId(openId);
        if (memberRegister != null) {
            String tid = IdWorkerUtil.generateStringId();
            String balanceListResult = paymentService.recordDetail(tid, recordId, memberRegister.getUserId(), memberRegister.getPhone());
            return balanceListResult;
        } else {
            throw new NoneRegisterException();
        }
    }

    @ApiOperation(value = "余额变更提醒接口")
    @Override
    public StateResult notifyBalanceUpdate(@RequestParam String userId, @RequestParam double balance) {
        ExeResult result = new ExeResult();
        LoggerManager.info("余额变更通过 => 开始,userId" + userId + ",balance:" + balance);

        MemberRegister register = memberRegisterService.getByUserId(userId);
        if (register != null) {
            wxService.updateCardBalance(register.getCardId(), register.getCode(), balance, "星链卡服务端余额变更通知", false);
            result.setSuccess(true);
            result.setMessage("余额通知成功");
        } else {
            //用户不存在，无需通知微信端
            result.setSuccess(true);
            result.setMessage("成功");
        }

        LoggerManager.info("余额变更通过 => 结束");
        return result.getResult();
    }

    @Override
    public StateResult balanceOrder(BalanceOrderRequest request) {
        ExeResult result = new ExeResult();
        try {
            if (StringUtil.isNullOrEmpty(request.getOpenId())) {
                throw new IllegalArgumentException("传入openid为空");
            }
            if (request.getTotalAmount() <= 0) {
                throw new IllegalArgumentException("传入金额为0");
            }

            MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());

            if (register == null) {
                throw new IllegalArgumentException("传入openid的关联用户还未注册");
            }

            String tid = IdWorkerUtil.generateStringId();
            String orderNo = paymentService.rechargeRequest(tid, register.getUserId(), register.getPhone(), request.getTotalAmount(), PaymentService.ORDER_REQUEST_TYPE_RECHARGE);

            LoggerManager.info("锁定帐户余额 => 开始");
            BalanceLockResult lockResult = paymentService.balanceLock(tid, register.getUserId(), register.getPhone(), request.getTotalAmount(), orderNo);

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public StateResult balancePay(BalancePayRequest request) {
        return null;
    }

    @ApiOperation(value = "设置支付密码")
    @Override
    public Object setPayPassword(@RequestBody PayPswdChangedRequest request) {

        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
        if (register != null) {
            //TODO 短信验证码校验
            String tid = IdWorkerUtil.generateStringId();
            return paymentService.setPayPassword(tid, register.getUserId(), request.getPaypswd(), register.getPhone());
        } else {
            throw new NoneRegisterException();
        }
    }

    @ApiOperation(value = "修改支付密码")
    @Override
    public Object changePayPassword(@RequestBody PayPswdChangedRequest request) {

        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
        if (register != null) {
            //TODO 短信验证码校验
            String tid = IdWorkerUtil.generateStringId();
            return paymentService.changePayPassword(tid, register.getUserId(), request.getOldPayPswd(), request.getPaypswd());
        } else {
            throw new NoneRegisterException();
        }
    }

    @ApiOperation(value = "重置支付密码")
    @Override
    public Object resetPayPassword(@RequestBody PayPswdChangedRequest request) {

        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
        if (register != null) {
            //TODO 短信验证码校验
            String tid = IdWorkerUtil.generateStringId();
            return paymentService.resetPayPassword(tid, register.getUserId(), request.getPaypswd());
        } else {
            throw new NoneRegisterException();
        }
    }
}
