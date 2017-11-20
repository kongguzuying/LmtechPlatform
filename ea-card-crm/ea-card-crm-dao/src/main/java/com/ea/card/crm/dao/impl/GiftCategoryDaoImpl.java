package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.GiftCategoryDao;
import com.ea.card.crm.mapper.GiftCategoryMapper;
import com.ea.card.crm.model.GiftCategory;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCategoryDaoImpl extends MyBatisDaoBase<GiftCategoryMapper, GiftCategory> implements GiftCategoryDao {
    @Override
    public List<GiftCategory> getCategoryList() {
        return baseMapper.selectCategoryList();
    }

    @Override
    public int existTitleName(String title){
        return baseMapper.existTitleName(title);
    }

    @Override
    public void deleteGiftRelation(String id) {
        baseMapper.deleteGiftRelation(id);
    }
}
