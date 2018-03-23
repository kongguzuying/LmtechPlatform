package com.ea.card.crm.service;

import com.ea.card.crm.model.GiftCategory;
import com.lmtech.service.DbManagerBase;

import java.util.List;

public interface GiftCategoryService extends DbManagerBase<GiftCategory> {
    List<GiftCategory> getCategoryList();
    boolean existTitleName(String title);
}
