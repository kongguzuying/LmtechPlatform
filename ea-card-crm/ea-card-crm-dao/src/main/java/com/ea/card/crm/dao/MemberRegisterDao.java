package com.ea.card.crm.dao;

import com.ea.card.crm.model.MemberRegister;
import com.lmtech.dao.Dao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MemberRegisterDao extends Dao<MemberRegister> {
    MemberRegister getByUserId(String userId);

    MemberRegister getByOpenId(String openId);

    MemberRegister getNewByOpenId(String openId);

    MemberRegister getByOfficialOpenIds(List<String> openIds);

    MemberRegister getByCode(String code);

    MemberRegister getByOpenIdAndIsDelete(String openId, int isDelete);

    MemberRegister selectByOfficialOpenIdAndIsDelete(String officialOpenId, int isDelete);

    MemberRegister getByOfficialOpenId(String officialOpenId);
    
    MemberRegister getByCodeAndIsDelete(String code, int isDelete);

    MemberRegister getByUserIdAndIsDelete(String userId, int isDelete);

    List<MemberRegister> getTrialOvertime();

    void updateTrialOvertime(@Param("ids") List<String> ids);

    void updateTrialOvertimeAndLevel(@Param("ids") List<String> ids);

    void updateTrialData(String userId, int mLevel, String cardBackground, boolean trial, Date trialDate, int trialDay);

    void updateRefreshToken(String openId, String refreshToken);

    void updateIsDelete(String id, int isDelete);

    void updateIntegral(String id, long integral);
}
