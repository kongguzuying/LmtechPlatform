package com.ea.card.crm.dao;

import com.ea.card.crm.model.IntegralLottery;
import com.lmtech.common.PageData;
import com.lmtech.dao.Dao;

import java.util.List;

public interface IntegralLotteryDao extends Dao<IntegralLottery> {
    PageData<IntegralLottery> getLatestPrizeList(int pageIndex, int pageSize);
}
