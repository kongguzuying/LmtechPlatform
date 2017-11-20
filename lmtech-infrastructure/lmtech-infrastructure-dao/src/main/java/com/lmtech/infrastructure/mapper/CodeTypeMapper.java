package com.lmtech.infrastructure.mapper;

import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.infrastructure.model.CodeType;

import java.util.List;

/**
 * 代码类型Mapper
 * Created by huang.jb on 2017-3-8.
 */
public interface CodeTypeMapper extends LmtechBaseMapper<CodeType> {
    /**
     * 搜索所有
     * @return
     */
    List<CodeType> selectAll();
}
