package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.LotteryProductDao;
import com.ea.card.crm.model.LotteryProduct;
import com.ea.card.crm.service.LotteryProductService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryProductServiceImpl extends AbstractDbManagerBaseImpl<LotteryProduct> implements LotteryProductService {

    @Autowired
    private LotteryProductDao lotteryProductDao;

    @Override
    public Dao getDao() {
        return lotteryProductDao;
    }

    @Override
    public List<LotteryProduct> getProducts() {
        return lotteryProductDao.getProducts();
    }

    @Override
    public boolean existName(String name) {
        int count = lotteryProductDao.existName(name);
        return count > 0;
    }
}
