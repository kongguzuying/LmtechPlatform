package com.ea.card.crm.mapper;

import com.ea.card.crm.model.CardCategory;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CardCategoryMapper extends LmtechBaseMapper<CardCategory> {
    List<CardCategory> selectCategoryList();

    List<CardCategory> selectChildCategoryList(@Param("parentId") String parentId);

    List<CardCategory> selectChildCategoryLists(@Param("parentIds") List<String> parentIds);
    int existTitleName(String title);
}
