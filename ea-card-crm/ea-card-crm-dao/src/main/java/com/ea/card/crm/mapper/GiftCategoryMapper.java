package com.ea.card.crm.mapper;

import com.ea.card.crm.model.GiftCategory;
import com.lmtech.dao.LmtechBaseMapper;

import java.util.List;

public interface GiftCategoryMapper extends LmtechBaseMapper<GiftCategory> {
    List<GiftCategory> selectCategoryList();
    int existTitleName(String title);
    void deleteGiftRelation(String id);
}
