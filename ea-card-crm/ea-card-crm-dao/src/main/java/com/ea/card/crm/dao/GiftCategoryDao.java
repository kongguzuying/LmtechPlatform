package com.ea.card.crm.dao;

import com.ea.card.crm.model.GiftCategory;
import com.lmtech.dao.Dao;

import java.util.List;

public interface GiftCategoryDao extends Dao<GiftCategory> {
    List<GiftCategory> getCategoryList();
    int existTitleName(String title);
    void deleteGiftRelation(String id);
}
