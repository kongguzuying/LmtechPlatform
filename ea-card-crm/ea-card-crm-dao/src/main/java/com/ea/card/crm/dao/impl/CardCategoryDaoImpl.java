package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.CardCategoryDao;
import com.ea.card.crm.mapper.CardCategoryMapper;
import com.ea.card.crm.model.CardCategory;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardCategoryDaoImpl extends MyBatisDaoBase<CardCategoryMapper, CardCategory> implements CardCategoryDao {
    @Override
    public List<CardCategory> getCategoryList() {
        return baseMapper.selectCategoryList();
    }

    @Override
    public List<CardCategory> getChildCategoryList(String parentId) {
        return baseMapper.selectChildCategoryList(parentId);
    }

    @Override
    public List<CardCategory> getChildCategoryList(List<String> parentIds) {
        return baseMapper.selectChildCategoryLists(parentIds);
    }

    @Override
    public int existTitleName(String title){
        return baseMapper.existTitleName(title);
    }
}
