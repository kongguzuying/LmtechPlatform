package com.lmtech.infrastructure.dao;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.model.Resource;

import java.util.List;

/**
 * 资源Dao
 * Created by huang.jb on 2017-4-20.
 */
public interface ResourceDao extends Dao<Resource> {
    /**
     * 搜索角色下的资源
     * @param roleId
     * @param param
     * @return
     */
    List<Resource> selectResourceInRole(String roleId, Resource param);

    /**
     * 搜索不在角色下的资源
     * @param roleId
     * @param param
     * @return
     */
    List<Resource> selectResourceNotInRole(String roleId, Resource param);

    /**
     * 删除资源关联关系
     * @param resourceId
     */
    void deleteResourceRelation(String resourceId);
}
