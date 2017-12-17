package com.ea.card.crm.mapper;

import com.ea.card.crm.model.MemberRegister;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MemberRegisterMapper extends LmtechBaseMapper<MemberRegister> {
    MemberRegister selectByUserId(@Param("userId") String userId);

    MemberRegister selectByOpenId(@Param("openId") String openId);

    MemberRegister selectByOfficialOpenIds(@Param("openIds") List<String> openIds);

    MemberRegister selectByCode(@Param("code") String code);

    MemberRegister selectByOpenIdAndIsDelete(@Param("openId") String openId,@Param("isDelete") int status);

    MemberRegister selectByOfficialOpenIdAndIsDelete(@Param("officialOpenId") String openId,@Param("isDelete") int status);

    MemberRegister selectByOfficialOpenId(@Param("officialOpenId") String officialOpenId);
    
    MemberRegister selectByCodeAndIsDelete(@Param("code") String code, @Param("isDelete") int status);

    MemberRegister selectByUserIdAndIsDelete(@Param("userId") String userId, @Param("isDelete") int status);

    List<MemberRegister> selectTrialOvertime(@Param("date") Date date);

    void updateTrialOvertime(@Param("date") Date date, @Param("ids") List<String> ids);

    void updateTrialOvertimeAndLevel(@Param("date") Date date, @Param("ids") List<String> ids);

    void updateTrialData(MemberRegister memberRegister);

    void updateRefreshToken(@Param("openId") String openId, @Param("refreshToken") String refreshToken);

    void updateIsDelete(@Param("id") String id, @Param("isDelete") int status);

    void updateIsDeleteBy(@Param("openId") String openId, @Param("isDelete") int status);
}
