package com.ea.card.crm.dao;

import com.ea.card.crm.model.CardCategory;
import com.lmtech.dao.Dao;

import java.util.List;

public interface CardCategoryDao extends Dao<CardCategory> {
    List<CardCategory> getCategoryList();

    List<CardCategory> getChildCategoryList(String parentId);

    List<CardCategory> getChildCategoryList(List<String> parentIds);

    int existTitleName(String title);
}
