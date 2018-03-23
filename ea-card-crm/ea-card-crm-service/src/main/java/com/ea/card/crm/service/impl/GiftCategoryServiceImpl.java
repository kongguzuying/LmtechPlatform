package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.GiftCategoryDao;
import com.ea.card.crm.model.GiftCategory;
import com.ea.card.crm.service.GiftCategoryService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class GiftCategoryServiceImpl extends AbstractDbManagerBaseImpl<GiftCategory> implements GiftCategoryService {

    @Autowired
    private GiftCategoryDao giftCategoryDao;

    @Override
    public Dao getDao() {
        return giftCategoryDao;
    }

    @Override
    public List<GiftCategory> getCategoryList() {
        return giftCategoryDao.getCategoryList();
    }

    @Override
    public boolean existTitleName(String title) {
        int count = giftCategoryDao.existTitleName(title);
        return count > 0;
    }

    @Override
    public void remove(Serializable id) {
        if (id == null) {
            throw new RuntimeException("id should not be null when remove");
        }
        giftCategoryDao.deleteGiftRelation(String.valueOf(id));
        super.remove(id);
    }
}
