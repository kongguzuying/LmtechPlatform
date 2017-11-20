package com.ea.card.crm.mapper;


import com.ea.card.crm.model.IntegralLottery;
import com.lmtech.dao.LmtechBaseMapper;

import java.util.List;

public interface IntegralLotteryMapper extends LmtechBaseMapper<IntegralLottery> {
    List<IntegralLottery> selectLatestPrizeList();
}
