package com.ea.card.crm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ea.card.crm.model.LotteryProduct;
import com.lmtech.dao.LmtechBaseMapper;

import java.util.List;

public interface LotteryProductMapper extends LmtechBaseMapper<LotteryProduct> {
    List<LotteryProduct> selectList();
    int existName(String name);
}
