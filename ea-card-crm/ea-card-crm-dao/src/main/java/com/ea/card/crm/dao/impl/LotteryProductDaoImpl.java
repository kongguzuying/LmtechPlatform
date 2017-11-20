package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.LotteryProductDao;
import com.ea.card.crm.mapper.LotteryProductMapper;
import com.ea.card.crm.model.LotteryProduct;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryProductDaoImpl extends MyBatisDaoBase<LotteryProductMapper, LotteryProduct> implements LotteryProductDao {
    @Override
    public List<LotteryProduct> getProducts() {
        return baseMapper.selectList();
    }
    @Override
    public int existName(String name){
        return baseMapper.existName(name);
    }
}
