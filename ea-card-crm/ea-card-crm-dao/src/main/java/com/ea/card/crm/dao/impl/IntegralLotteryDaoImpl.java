package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.IntegralLotteryDao;
import com.ea.card.crm.mapper.IntegralLotteryMapper;
import com.ea.card.crm.model.IntegralLottery;
import com.lmtech.common.PageData;
import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.util.PageHelper;
import org.springframework.stereotype.Service;

@Service
public class IntegralLotteryDaoImpl extends MyBatisDaoBase<IntegralLotteryMapper, IntegralLottery> implements IntegralLotteryDao {
    public PageData<IntegralLottery> getLatestPrizeList(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        baseMapper.selectLatestPrizeList();
        PageData<IntegralLottery> pageData = PageHelper.endPage();
        return pageData;
    }
}
