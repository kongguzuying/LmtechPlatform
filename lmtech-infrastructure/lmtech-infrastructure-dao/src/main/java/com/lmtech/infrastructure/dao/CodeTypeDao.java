package com.lmtech.infrastructure.dao;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.model.CodeType;

import java.util.List;

/**
 * 代码类型Dao
 * Created by huang.jb on 2017-3-8.
 */
public interface CodeTypeDao extends Dao<CodeType> {
    /**
     * 搜索所有
     * @return
     */
    List<CodeType> selectAll();
}
