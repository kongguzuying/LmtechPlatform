package com.ea.card.crm.dao;

import com.ea.card.crm.model.LotteryProduct;
import com.lmtech.dao.Dao;

import java.util.List;

public interface LotteryProductDao extends Dao<LotteryProduct> {
    List<LotteryProduct> getProducts();
    int existName(String name);
}
