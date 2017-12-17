package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.MemberRegisterDao;
import com.ea.card.crm.dao.WxActiveMessageDao;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.model.WxActiveMessage;
import com.ea.card.crm.service.CodeAdaptorService;
import com.ea.card.crm.service.MemberRegisterService;
import com.ea.card.crm.service.WxService;
import com.ea.card.crm.service.exception.NoneRegisterException;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import com.lmtech.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemberRegisterServiceImpl extends AbstractDbManagerBaseImpl<MemberRegister> implements MemberRegisterService {

    @Autowired
    private MemberRegisterDao memberRegisterDao;
    @Autowired
    private WxActiveMessageDao wxActiveMessageDao;
    @Autowired
    private WxService wxService;
    @Autowired
    private CodeAdaptorService codeAdaptorService;

    @Override
    public Dao getDao() {
        return memberRegisterDao;
    }

    @Override
    public MemberRegister getByUserId(String userId) {
        return memberRegisterDao.getByUserId(userId);
    }

    @Override
    public MemberRegister getByOpenId(String openId) {
        return memberRegisterDao.getByOpenId(openId);
    }

    @Override
    public MemberRegister getNewByOpenId(String openId) {
        return memberRegisterDao.getNewByOpenId(openId);
    }


    @Override
    public MemberRegister getByCode(String code) {
        return memberRegisterDao.getByCode(code);
    }

    @Override
    public MemberRegister getByOpenIdAndIsDelete(String openId, int isDelete) {
        return memberRegisterDao.getByOpenIdAndIsDelete(openId, isDelete);
    }

    @Override
    public String getOfficialOpenIdOfRegister(String fromUserName) {
        WxActiveMessage wxActiveMessage = wxActiveMessageDao.getByFromUserName(fromUserName);
        if (wxActiveMessage != null && !StringUtil.isNullOrEmpty(wxActiveMessage.getAppletOpenId())) {
            List<String> openIds = new ArrayList<>();
            openIds.add(wxActiveMessage.getFromUserName());
            openIds.add(wxActiveMessage.getAppletOpenId());
            MemberRegister register = memberRegisterDao.getByOfficialOpenIds(openIds);

            if (register != null) {
                return register.getOfficialOpenId();
            } else {
                throw new NoneRegisterException();
            }
        } else {
            return fromUserName;
        }
    }

    @Override
    public MemberRegister selectByOfficialOpenIdAndIsDelete(String officialOpenId, int isDelete) {
        return memberRegisterDao.selectByOfficialOpenIdAndIsDelete(officialOpenId, isDelete);
    }

    @Override
    public MemberRegister getByOfficialOpenId(String officialOpenId) {
        return memberRegisterDao.getByOfficialOpenId(officialOpenId);
    }

    @Override
    public void updateRefreshToken(String openId, String refreshToken) {
        memberRegisterDao.updateRefreshToken(openId, refreshToken);
    }

    @Override
    public void updateIsDelete(String id, int isDelete) {
        memberRegisterDao.updateIsDelete(id, isDelete);
    }

    @Override
    public MemberRegister getByCodeAndIsDelete(String code, int isDelete) {
        return memberRegisterDao.getByCodeAndIsDelete(code, isDelete);
    }

    @Override
    public MemberRegister getByUserIdAndIsDelete(String userId, int isDelete) {
        return memberRegisterDao.getByUserIdAndIsDelete(userId, isDelete);
    }

    @Override
    public void setTrailOvertime() {
        LoggerManager.info("设置试用超时状态 => 开始");
        List<MemberRegister> memberRegisters = memberRegisterDao.getTrialOvertime();

        if (!CollectionUtil.isNullOrEmpty(memberRegisters)) {
            List<String> updateOvertimeIds = new ArrayList<>();
            List<String> updateOvertimeAndLevelIds = new ArrayList<>();
            List<MemberRegister> updateOvertimeAndLevelEntitys = new ArrayList<>();

            for (MemberRegister memberRegister : memberRegisters) {
                Date endTrialDate = DateUtil.addDay(memberRegister.getTrialDate(), memberRegister.getTrialDay());
                double subDays = 0;
                if (endTrialDate.compareTo(memberRegister.getEndDate()) > 0) {
                    subDays = Math.abs(DateUtil.getDaySub(memberRegister.getEndDate(), endTrialDate));
                } else if (endTrialDate.compareTo(memberRegister.getEndDate()) < 0) {
                    subDays = Math.abs(DateUtil.getDaySub(endTrialDate, memberRegister.getEndDate()));
                }

                if (subDays < 1) {
                    updateOvertimeAndLevelIds.add(memberRegister.getId());
                    updateOvertimeAndLevelEntitys.add(memberRegister);
                } else {
                    updateOvertimeIds.add(memberRegister.getId());
                }
            }

            LoggerManager.info("更新超时状态数:" + updateOvertimeIds.size() + ",Json:" + JsonUtil.toJson(updateOvertimeIds));
            LoggerManager.info("更新超时状态及等级信息数:" + updateOvertimeAndLevelIds.size() + ",json:" + JsonUtil.toJson(updateOvertimeAndLevelIds));
            memberRegisterDao.updateTrialOvertime(updateOvertimeIds);
            memberRegisterDao.updateTrialOvertimeAndLevel(updateOvertimeAndLevelIds);

            //通知微信修改等级
            if (!CollectionUtil.isNullOrEmpty(updateOvertimeAndLevelEntitys)) {
                LoggerManager.info("通知微信更新用户等级信息 => 开始");
                String mLevelName = codeAdaptorService.getNameByCodeItemValue(String.valueOf(MemberRegister.MLEVEL_NORMAL));
                for (MemberRegister memberRegister : updateOvertimeAndLevelEntitys) {
                    String cardId = memberRegister.getCardId();
                    String code = memberRegister.getCode();
                    try {
                        wxService.updateCardLevel(cardId, code, mLevelName, 1, null, null, false);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        LoggerManager.error(e);
                    } catch (Exception e) {
                        LoggerManager.error(e);
                        LoggerManager.error("retry handle : wxService.updateCardLevel(" + cardId + ", " + code + ", " + mLevelName + ", 1, null, null, false);");
                    }
                }
                LoggerManager.info("通知微信更新用户等级信息 => 结束");
            }
        } else {
            LoggerManager.info("不存在任何试用中的用户，操作结束");
        }
        LoggerManager.info("设置试用超时状态 => 结束");
    }
}
