package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.CardCategoryDao;
import com.ea.card.crm.model.CardCategory;
import com.ea.card.crm.service.CardCategoryService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import com.lmtech.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardCategoryServiceImpl extends AbstractDbManagerBaseImpl<CardCategory> implements CardCategoryService {

    @Autowired
    private CardCategoryDao cardCategoryDao;

    @Override
    public Dao getDao() {
        return cardCategoryDao;
    }

    @Override
    public List<CardCategory> getCategoryList() {
        return cardCategoryDao.getCategoryList();
    }

    @Override
    public List<CardCategory> getCategoryAndChildrensList() {
        List<CardCategory> categories = cardCategoryDao.getCategoryList();
        if (!CollectionUtil.isNullOrEmpty(categories)) {
            Map<String, CardCategory> categoryMap = new HashMap();
            List<String> parentIds = new ArrayList<>();
            for (CardCategory category : categories) {
                parentIds.add(category.getId());
                categoryMap.put(category.getId(), category);
            }

            List<CardCategory> childCategorys = cardCategoryDao.getChildCategoryList(parentIds);
            if (!CollectionUtil.isNullOrEmpty(childCategorys)) {
                for (CardCategory childCategory : childCategorys) {
                    CardCategory parentCategory = categoryMap.get(childCategory.getParentId());
                    parentCategory.addChildren(childCategory);
                }
            }
        }
        return categories;
    }

    @Override
    public List<CardCategory> getChildCategoryList(String parentId) {
        return cardCategoryDao.getChildCategoryList(parentId);
    }

    @Override
    public List<CardCategory> getChildCategoryList(List<String> parentIds) {
        return cardCategoryDao.getChildCategoryList(parentIds);
    }

    @Override
    public boolean existTitleName(String title) {
        int count = cardCategoryDao.existTitleName(title);
        return count > 0;
    }
}
