package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.CodeItemDao;
import com.lmtech.infrastructure.mapper.CodeItemMapper;
import com.lmtech.infrastructure.model.CodeItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 代码项Dao
 * Created by huang.jb on 2017-3-8.
 */
@Service
public class CodeItemDaoImpl extends MyBatisDaoBase<CodeItemMapper, CodeItem> implements CodeItemDao {

    @Override
    public List<CodeItem> selectAll() {
        return baseMapper.selectAll();
    }

    @Override
    public List<CodeItem> selectOfType(String typeCode) {
        return baseMapper.selectOfType(typeCode);
    }

    @Override
    public void deleteByTypeCode(String typeCode) {
        baseMapper.deleteByTypeCode(typeCode);
    }
}
