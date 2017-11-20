package com.ea.card.crm.service;

import com.ea.card.crm.model.LotteryProduct;
import com.lmtech.service.DbManagerBase;

import java.util.List;

public interface LotteryProductService extends DbManagerBase<LotteryProduct> {
    List<LotteryProduct> getProducts();
    boolean existName(String name);
}
