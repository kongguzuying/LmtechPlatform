package com.ea.card.crm.service;

import com.ea.card.crm.model.CardCategory;
import com.lmtech.service.DbManagerBase;

import java.util.List;

public interface CardCategoryService extends DbManagerBase<CardCategory> {
    List<CardCategory> getCategoryList();

    List<CardCategory> getCategoryAndChildrensList();

    List<CardCategory> getChildCategoryList(String parentId);

    List<CardCategory> getChildCategoryList(List<String> parentIds);

    boolean existTitleName(String title);
}
