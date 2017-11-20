package com.ea.card.crm.dao.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ea.card.crm.dao.MemberRegisterDao;
import com.ea.card.crm.mapper.MemberRegisterMapper;
import com.ea.card.crm.model.MemberRegister;
import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.DateUtil;
import com.lmtech.util.StringUtil;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MemberRegisterDaoImpl extends MyBatisDaoBase<MemberRegisterMapper, MemberRegister> implements MemberRegisterDao {
    @Override
    public MemberRegister getByUserId(String userId) {
        return baseMapper.selectByUserId(userId);
    }

    @Override
    public MemberRegister getByOpenId(String openId) {
        return baseMapper.selectByOpenId(openId);
    }

    @Override
    public MemberRegister getNewByOpenId(String openId) {
        EntityWrapper<MemberRegister> ew = new EntityWrapper();
        ew.eq("open_id", openId).orderBy("create_date", false);
        return baseMapper.selectList(ew).get(0);
    }

    @Override
    public MemberRegister getByOfficialOpenIds(List<String> openIds) {
        if (!CollectionUtil.isNullOrEmpty(openIds)) {
            return baseMapper.selectByOfficialOpenIds(openIds);
        } else {
            return null;
        }
    }

    @Override
    public MemberRegister getByCode(String code) {
        return baseMapper.selectByCode(code);
    }

    @Override
    public MemberRegister getByOpenIdAndIsDelete(String openId, int isDelete) {
        return baseMapper.selectByOpenIdAndIsDelete(openId, isDelete);
    }

    @Override
    public MemberRegister selectByOfficialOpenIdAndIsDelete(String officialOpenId, int isDelete) {
        return baseMapper.selectByOfficialOpenIdAndIsDelete(officialOpenId, isDelete);
    }

    @Override
    public MemberRegister getByOfficialOpenId(String officialOpenId) {
        return baseMapper.selectByOfficialOpenId(officialOpenId);
    }

    @Override
    public void updateTrialData(String userId, int mLevel, String cardBackground, boolean trial, Date trialDate, int trialDay) {
        MemberRegister register = new MemberRegister();
        register.setUserId(userId);
        register.setmLevel(mLevel);
        register.setTrial(trial);
        register.setTrialDate(trialDate);
        register.setTrialDay(trialDay);
        if(!StringUtil.isNullOrEmpty(cardBackground)) {
        	register.setCardBackground(cardBackground);
        }
        register.setUpdateDate(new Date());
        register.setForever(false);
        register.increaseDataVersion();
        Date trialEndDate = DateUtil.addDay(trialDate, trialDay);
        register.setBeginDate(trialDate);
        register.setEndDate(trialEndDate);
        baseMapper.updateTrialData(register);
    }

    @Override
    public void updateRefreshToken(String openId, String refreshToken) {
        baseMapper.updateRefreshToken(openId, refreshToken);
    }

    @Override
    public void updateIsDelete(String id, int isDelete) {
        baseMapper.updateIsDelete(id, isDelete);
    }

	@Override
	public MemberRegister getByCodeAndIsDelete(String code, int isDelete) {
		return baseMapper.selectByCodeAndIsDelete(code, isDelete);
	}

    @Override
    public List<MemberRegister> getTrialOvertime() {
        return baseMapper.selectTrialOvertime(new Date());
    }

    @Override
    public void updateTrialOvertime(List<String> ids) {
        if (!CollectionUtil.isNullOrEmpty(ids)) {
            baseMapper.updateTrialOvertime(new Date(), ids);
        }
    }

    @Override
    public void updateTrialOvertimeAndLevel(List<String> ids) {
        if (!CollectionUtil.isNullOrEmpty(ids)) {
            baseMapper.updateTrialOvertimeAndLevel(new Date(), ids);
        }
    }
}
