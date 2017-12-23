package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.TenancyDao;
import com.lmtech.infrastructure.mapper.TenancyMapper;
import com.lmtech.infrastructure.model.Tenancy;
import org.springframework.stereotype.Service;

@Service
public class TenancyDaoImpl extends MyBatisDaoBase<TenancyMapper, Tenancy> implements TenancyDao {
    @Override
    public Tenancy getByCode(String code) {
        return baseMapper.selectByCode(code);
    }
}
