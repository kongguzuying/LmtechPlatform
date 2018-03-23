package com.lmtech.infrastructure.dao;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.model.CodeItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代码项Dao
 * Created by huang.jb on 2017-3-8.
 */
public interface CodeItemDao extends Dao<CodeItem> {
    /**
     * 查询所有代码项
     * @return
     */
    List<CodeItem> selectAll();

    /**
     * 查询某一代码类别下的所有代码项
     * @param typeCode
     * @return
     */
    List<CodeItem> selectOfType(String typeCode);

    /**
     * 删除某一代码类别下的所有代码项
     * @param typeCode
     */
    void deleteByTypeCode(String typeCode);
}
