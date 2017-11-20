package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.CodeTypeDao;
import com.lmtech.infrastructure.mapper.CodeTypeMapper;
import com.lmtech.infrastructure.model.CodeType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huang.jb on 2017-3-8.
 */
@Service
public class CodeTypeDaoImpl extends MyBatisDaoBase<CodeTypeMapper, CodeType> implements CodeTypeDao {
    @Override
    public List<CodeType> selectAll() {
        return baseMapper.selectAll();
    }
}
